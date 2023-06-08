// pages/shopcar.js
const http = require('./utils/http.js');
const app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    foodData:[],
    showShopNum:[],
    showFoodData:[],
    orderData:{},
    eatType:"TS",
    totalNum:0,
    totalPrice:0,
    addressData:[],
    curAddress:{},
    noAddress:false,
    showAddress:false,
    showLoading:true
  },

  chooseAddress: function (){
  },

  updateCurAddress: function(e){
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  getAddress: function(){
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.getTypeData();
    this.setData({
      imageUrl:app.globalData.imageUrl,
      orderSuccess:false,
      orderError:false,
      noFood:false,
      delShopCar:false
    })
  },

  closeAddress: function (){
    this.setData({
      showAddress:false
    });
  },

  unLoading: function(){
    if (this.data.showLoading){
      this.data.showLoading=false;
      this.setData({
        showLoading:false
      })
    }
  },

  showDelShopCar: function(){
    this.setData({
      delShopCar:true
    })
  },

  getTypeData: function(){
  },

  showShopNum:function(data){
  },

  handleChange: function(e){
    this.data.eatType=e.detail.value;
    this.setData({
      eatType:this.data.eatType
    })
  },

  addShopNum: function(e){
  },

  reduceShopNum: function(e){
  },

  addOrder:function(){
    this.setData({
      showLoading:false,
      orderError:true
    })
  },

  clearShopCar: function(){
  },

  changeAddress: function(){
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getAddress();
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