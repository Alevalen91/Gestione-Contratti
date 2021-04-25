
$(document).ready(function(){


 let data = sessionStorage.getItem('User');
 if (data != "logged"){
	 alert("Effettua prima il Login");
   window.location.replace("Login.html")
 

 }

 else {
    document.getElementsByTagName("html")[0].style.visibility = "visible";
 }

   });

   
    
        $(document).ready(function() {

$("#logout").click(function() {

sessionStorage.removeItem('User')
   window.location.replace("Login.html")
   alert("Utente disconnesso");


});
});
