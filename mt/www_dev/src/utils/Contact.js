/**
 * Created by zxf on 2017/8/31.
 */
const contactPlugin = {}

contactPlugin.pickContact = function(onSuccess, onFail){
  navigator.contacts.pickContact(function(contact){
    console.log('The following contact has been selected:' + JSON.stringify(contact));
    onSuccess(contact)
  },function(err){
    console.log('Error: ' + err);
  });
}

export default contactPlugin
