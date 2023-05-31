const app=getApp();
function request(url,method,params,header,onsuccess){
  var token = wx.getStorageSync("token");
  if (token == null || token== ""){
    login(url,method,params,header,onsuccess);
  }else{
    header.Authorization=token;
    wx.request({
      url: app.globalData.apiUrl+url,
      method:method,
      data: params,
      header:header,
      success: function(response){
        if (response.data.code=="401"){
          login(url,method,params,onsuccess);
        }else{
          onsuccess(response);
        }
      }
    })
  }
}

function login(url,method,params,header,onsuccess){
  wx.login({
    success (res) {
      console.log(res);
      if (res.code) {
        //发起网络请求
        wx.request({
          url: app.globalData.apiUrl+"/authority/wxLogin",
          method:"POST",
          header:{
            "content-type":"application/x-www-form-urlencoded"
          },
          data: {
            code: res.code
          },
          success: function(response){
            console.log("登录成功token:"+response.data.data.token);
            wx.setStorageSync("token", response.data.data.token);
            request(url,method,params,header,onsuccess);
          }
        })
      }
    }
  })
}

module.exports = {
  request
}