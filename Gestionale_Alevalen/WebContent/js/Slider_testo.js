
  var sliderhome = [ "controllo",
  " <a href = 'grid.html' ><h3 classe 'h3link'>Inserimento</h3></a></span>",
    "  <a href = 'Analisi.html'> <h3 classe 'h3link'>Analisi</h3></a></span>",
   " <a href = 'Gestione_promo.html'> <h3 classe 'h3link'>Promo</h3> </a></span>",
    ];
var sliderp = ["controllo",
   " Gestisci\Visualizza  record contratti",
"Analisi punteggi contratti",
"Gestisci informazioni promo per i collaboratori",
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
    document.getElementById("parag-slider").innerHTML = sliderp[i];
    console.log("sono slider");
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
    document.getElementById("parag-slider").innerHTML = sliderp[i];
  }
else {  i--;
  document.getElementById("slider_id").innerHTML = sliderhome[i] ;
  document.getElementById("parag-slider").innerHTML = sliderp[i];
}
}
