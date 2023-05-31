const http = require('./utils/http.js');
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderData:[],
    imageUrl:app.globalData.imageUrl,
    showLoading:true
  },

  unLoading: function(){
    if (this.data.showLoading){
      this.data.showLoading=false;
      this.setData({
        showLoading:false
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  toOrderInfo: function(e){
    wx.navigateTo({
      url: 'orderInfo?id='+e.currentTarget.dataset.id,
    })
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
    http.request(
      "/specific/getUserOrder",
      "GET",
      {},
      {
        "content-type":"application/x-www-form-urlencoded"
      },
      (res)=>{
        this.data.orderData=res.data.data;
        for (var i=0;i<this.data.orderData.length;i++){
          this.data.orderData[i].createtime=this.timestampToTime(this.data.orderData[i].createtime);
          if (this.data.orderData[i].status=="已接单"){
            this.data.orderData[i].color="black";
          }else if (this.data.orderData[i].status=="订单取消"){
            this.data.orderData[i].color="red";
          }else if (this.data.orderData[i].status=="订单完成"){
            this.data.orderData[i].color="blue";
          }
        }
        this.setData({
          orderData:this.data.orderData
        })
      }
    )

  },

  timestampToTime: function(time) {
    var date = new Date(time);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1):date.getMonth()+1) + '-';
    var D = (date.getDate()< 10 ? '0'+date.getDate():date.getDate())+ ' ';
    var h = (date.getHours() < 10 ? '0'+date.getHours():date.getHours())+ ':';
    var m = (date.getMinutes() < 10 ? '0'+date.getMinutes():date.getMinutes()) + ':';
    var s = date.getSeconds() < 10 ? '0'+date.getSeconds():date.getSeconds();
    return Y+M+D+h+m+s;
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