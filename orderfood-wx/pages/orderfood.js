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
    let scrollTop = e.detail.scrollTop; //获取距离顶部的距离
    for (var i=0;i<this.data.typeData.length;i++){
      if (this.data.typeData[i].minHeight<=scrollTop
        && this.data.typeData[i].maxHeight>(scrollTop+5)){
          this.setData({
            "curSelectType":this.data.typeData[i].id
          })
          break;
      }
    }
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
      shopNum.push(data);
    }
    wx.setStorageSync("shopNum",shopNum);
    this.showShopNum(this.data.foodData);
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
    this.showShopNum(this.data.foodData);
  },

  getTypeData: function(){
    http.request(
      "/specific/getWxFoodTypeAll",
      "GET",
      {},
      {
        "content-type":"application/x-www-form-urlencoded"
      },
      (res)=>{
        for (var i=0;i<res.data.data.typeData.length;i++){
          res.data.data.typeData[i].id="wx-"+res.data.data.typeData[i].id;
        }
        for (var i=0;i<res.data.data.foodData.length;i++){
          if (res.data.data.foodData[i].type=="0"){
            res.data.data.foodData[i].id="wx-"+res.data.data.foodData[i].id;
          }else if (res.data.data.foodData[i].type=="1"){
            res.data.data.foodData[i].typeid="wx-"+res.data.data.foodData[i].typeid;
          }
        }
        for (var i=0;i<res.data.data.typeData.length;i++){
          if (i==0){
            res.data.data.typeData[i].minHeight=0;
          }else{
            res.data.data.typeData[i].minHeight=res.data.data.typeData[i-1].maxHeight;
          }
          res.data.data.typeData[i].maxHeight=res.data.data.typeData[i].minHeight+this.data.typeHeight+this.data.foodHeight*res.data.data.typeData[i].num;
        }
        if (res.data.data.foodData.length>0){
          this.setData({
            "curSelectType":res.data.data.foodData[0].id,
            "curSelectFoodType":res.data.data.foodData[0].id
          })
        }
        this.data.typeData=res.data.data.typeData;
        this.data.foodData=res.data.data.foodData;
        this.setData({
          "typeData":res.data.data.typeData
        })
        this.showShopNum(this.data.foodData);
      }
    )
  },

  showShopNum:function(data){
    var shopNum = wx.getStorageSync("shopNum");
    var shopCarNum=0;
    for (var i=0;i<data.length;i++){
      if (data[i].type=="1"){
        data[i].shopNum=0;
        if (shopNum!=null && shopNum!="" && shopNum.length>0){
          for (var j=0;j<shopNum.length;j++){
            if (data[i].id == shopNum[j].id){
              data[i].shopNum = shopNum[j].num;
            }
          }
        }
      }
    }
    for (var i=0;i<shopNum.length;i++){
      shopCarNum = shopCarNum + shopNum[i].num;
    }
    for (var i=0;i<this.data.typeData.length;i++){
      this.data.typeData[i].shopNum=0;
      for (var j=0;j<shopNum.length;j++){
        if (this.data.typeData[i].id==shopNum[j].typeid){
          this.data.typeData[i].shopNum+=shopNum[j].num;
        }
      }
    }
    this.setData({
      "foodData":data,
      "shopCarNum":shopCarNum,
      "typeData":this.data.typeData
    })
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