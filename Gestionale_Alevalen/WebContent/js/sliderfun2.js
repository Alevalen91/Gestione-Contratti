var sliderhome = [ "controllo",
 " <a href = 'grid.html' ><h3 classe 'h3link'>Sky</h3></a></span>",
   "  <a href = 'fastweb.html'> <h3 classe 'h3link'>Fastweb</h3></a></span>",
  " <a href = 'Linkem.html'> <h3 classe 'h3link'>Linkem</h3> </a></span>",
   ];

var page_counter = 0
var counter =0;
var stp;
var i = 0;




 function start() {
     stp = setInterval(sliderfun, 3000);

     }

start();

function sliderfun() {

 if (i == 3 || i == 0){
     i = 0;

 }

   i++;
   document.getElementById("slider_id").innerHTML = sliderhome[i] ;


   console.log(i);





}
sliderfun();





function stop() {
 clearInterval(stp)
};



function retro(){
 if(i==1){
   i=3
   document.getElementById("slider_id").innerHTML = sliderhome[i] ;

 }
else {  i--;
 document.getElementById("slider_id").innerHTML = sliderhome[i] ;

}
}
