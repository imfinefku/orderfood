const http = require('./utils/http.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    vipLevel:[],
    curVipName:"",
    curVipSize:"",
    showMessage:false,
    username:"爱吃火锅",
    showLoading:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getUserLevel();
    var username = wx.getStorageSync("username");
    if (username!=null && username!=""){
      this.data.username=username;
    }
    this.setData({
      username:this.data.username
    })
  },

  getUserLevel: function(){
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  searchMessage: function(){
    this.setData({
      showMessage:true
    })
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