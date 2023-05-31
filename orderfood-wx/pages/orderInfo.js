const http = require('./utils/http.js');
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderData:{},
    imageUrl:app.globalData.imageUrl
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    http.request(
      "/specific/getOrderById",
      "GET",
      {
        "id":options.id
      },
      {
        "content-type":"application/x-www-form-urlencoded"
      },
      (res)=>{
        this.data.orderData=res.data.data;
        this.data.orderData.createtime=this.timestampToTime(this.data.orderData.createtime);
        this.setData({
          orderData:this.data.orderData
        })
      }
    )
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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})