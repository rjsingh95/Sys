'use strict'

const functions = require('firebase-functions');
const _ = require('lodash');
const request = require('request-promise')

const admin =require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.indexPostsToElastic = functions.database.ref('/products/{product_id}')
	.onWrite(( change,context) => {

         const productData = change.after.val();
         const product_id = context.params.product_id;
		
		console.log('Indexing post:', productData);
		let elasticsearchFields = ['brand','c_b','category','level','price','product_code',
			'product_id','rating','search_tags','title'];
		let elasticSearchConfig = functions.config().elasticsearch;
		let elasticSearchUrl = elasticSearchConfig.url + 'products/post/' + product_id;
		let elasticSearchMethod = productData ? 'POST' : 'DELETE';
		
		let elasticSearchRequest = {
			method: elasticSearchMethod,
			url: elasticSearchUrl,
			auth:{
				username: elasticSearchConfig.username,
				password: elasticSearchConfig.password,
			},
			body: _.pick(productData, elasticsearchFields),
			json: true
	

		  };
		  
		  return request(elasticSearchRequest).then(response => console.log("ElasticSearch response", response));
	

	});


exports.indexBooksToElastic = functions.database.ref('/books/{product_id}')
	.onWrite(( change,context) => {

         const productData = change.after.val();
         const product_id = context.params.product_id;
		
		console.log('Indexing post:', productData);
		let elasticsearchFields = ['author','book_id','title'];
		let elasticSearchConfig = functions.config().elasticsearch;
		let elasticSearchUrl = elasticSearchConfig.url + 'books/book/' + product_id;
		let elasticSearchMethod = productData ? 'POST' : 'DELETE';
		
		let elasticSearchRequest = {
			method: elasticSearchMethod,
			url: elasticSearchUrl,
			auth:{
				username: elasticSearchConfig.username,
				password: elasticSearchConfig.password,
			},
			
			body: _.pick(productData, elasticsearchFields),
			json: true
		  };
		  
		  return request(elasticSearchRequest).then(response => console.log("ElasticSearch response", response));
	});
exports.sendNotification=functions.database.ref('/notifications/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/users/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Delivery Request",
        body: "You have  a new Delivery Request",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});



exports.Cancelshop=functions.database.ref('/cancelshop/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/customers/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Delivery Cancelled",
        body: "Delivery Cancelled By Seller",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});


exports.Cancelcustomer=functions.database.ref('/cancelcustomer/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/users/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Delivery Request Cancelled",
        body: "Delivery Cancelled By customer",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});



exports.sendNotificationbargain=functions.database.ref('/notifications_bargain/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/users/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Your Deal is reviewed",
        body: "Check the updation in your deal by customer",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});

exports.shopaccepted=functions.database.ref('/acceptshop/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/customers/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Your Deal is accepted",
        body: "Your Product will be delivered\n soon",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});

exports.customeraccepted=functions.database.ref('/acceptcustomer/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/users/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Your Deal is accepted",
        body: "Delivery is expected \n soon",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});
exports.bargaingtocustomer=functions.database.ref('/notificationsc/{user_id}/{notification_id}').onWrite(( change,context) => {
    
            

 const user_id= context.params.user_id;
 const notification =change.after.val();

 console.log('We have a notification to send to:', user_id);


  if(!change.after.val()){
  return console.log('A notification has been deleted from database :', notification_id);
  
  }


 const deviceToken= admin.database().ref(`/customers/${user_id}/device_token`).once('value');
 return deviceToken.then(result =>{
     const token_id=result.val();

      const payload ={
       notification:{
        title: "Your Deal is reviewed",
        body: "Check the updation in your deal by shopkeeper",
        icon:"default"
    }
       };
    return admin.messaging().sendToDevice(token_id, payload).then(response =>{

    return console.log('Error in notification delivery');

 });

 });

});


