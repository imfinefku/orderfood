const http = require('./utils/http.js');
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchWord:"",
    searchData:[],
    imageUrl:app.globalData.imageUrl,
    foodHeight:100,
    noDataFlag:false
  },

  changeSearchWorld:function(e){
    this.data.searchWord=e.detail.value;
    this.searchData();
  },

  searchData: function(){
    var str=this.data.searchWord;
    if (str!=null && str!=""){
      http.request(
        "/specific/getFoodSearch",
        "GET",
        {
          "searchWord":str
        },
        {
          "content-type":"application/x-www-form-urlencoded"
        },
        (res)=>{
          this.data.searchData=res.data.data;
          for (var i=0;i<this.data.searchData.length;i++){
            this.data.searchData[i].typeid="wx-"+this.data.searchData[i].typeid;
          }
          this.showShopNum(this.data.searchData);
        }
      )
    }else{
      this.data.searchData=[];
      this.showShopNum(this.data.searchData);
    }
  },

  showShopNum:function(data){
    var shopNum = wx.getStorageSync("shopNum");
    for (var i=0;i<data.length;i++){
      data[i].shopNum=0;
      if (shopNum!=null && shopNum!="" && shopNum.length>0){
        for (var j=0;j<shopNum.length;j++){
          if (data[i].id == shopNum[j].id){
            data[i].shopNum = shopNum[j].num;
          }
        }
      }
    }
    this.setData({
      searchData:data,
      noDataFlag:false
    })
    if (data.length==0 && this.data.searchWord!=null && this.data.searchWord!=""){
      this.setData({
        noDataFlag:true
      })
    }
  },

  addShopNum: function(e){
    var shopNum = wx.getStorageSync("shopNum");
    var flag=false;
    if (shopNum!=null && shopNum!="" && shopNum.length>0){
      for (var i=0;i<shopNum.length;i++){
        if (shopNum[i].id==e.currentTarget.dataset.id){
          shopNum[i].num++;
          shopNum[i].typeid=e.currentTarget.dataset.typeid;
          flag=true;
          break;
        }
      }
    }else{
      shopNum=[];
    }
    if (!flag){
      var data={"id":e.currentTarget.dataset.id,"num":1,"typeid":e.currentTarget.dataset.typeid};
      console.log(data);
      shopNum.push(data);
    }
    wx.setStorageSync("shopNum",shopNum);
    this.showShopNum(this.data.searchData);
  },

  reduceShopNum: function(e){
    var shopNum = wx.getStorageSync("shopNum");
    if (shopNum!=null && shopNum!="" && shopNum.length>0){
      for (var i=0;i<shopNum.length;i++){
        if (shopNum[i].id==e.currentTarget.dataset.id){
          if (shopNum[i].num>0){
            shopNum[i].num--;
            shopNum[i].typeid=e.currentTarget.dataset.typeid;
          }
          break;
        }
      }
    }
    wx.setStorageSync("shopNum",shopNum);
    this.showShopNum(this.data.searchData);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})