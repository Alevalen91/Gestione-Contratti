$(document).ready(function(){
   $('#Invia').click(function () {
     let obj;
     $.ajax({

           type: 'POST',
           url: '/LoginServlet',
           data: {user: $("#user").val() , password: $("#password").val()},
           dataType: 'json',
           success: function (data) {

             if (data == true){
               sessionStorage.setItem('User', 'logged');
               window.location.replace("Index.html")
               console.log(sessionStorage.getItem('User'));

             }
             else {
               alert("user o password non corretti");
             }
           }
       });

   });
   });






   
