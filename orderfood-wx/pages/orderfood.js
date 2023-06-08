const http = require('./utils/http.js');
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    typeData:[],
    foodData:[],
    typeHeight:35,
    foodHeight:100,
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

  getCommodity: function(e){
    this.setData({
      "curSelectType":e.currentTarget.dataset.id,
      "curSelectFoodType":e.currentTarget.dataset.id
    })
  },
  
  watchScroll(e) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  toSearch: function(){
    wx.navigateTo({
      url: 'orderFoodSearch',
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      "imageUrl":app.globalData.imageUrl
    })
    this.getTypeData();
  },

  addShopNum: function(e){
  },

  reduceShopNum: function(e){
  },

  getTypeData: function(){
  },

  showShopNum:function(data){
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