var start = 0;


      $(document).ready(function () {
        var start = 0;
        var nameValue = document.getElementById("uniqueID").value;




        console.log(start);
          // Create vertical jqxProgressBar.
          $("#jqxprogressbar").jqxProgressBar({ orientation: 'vertical', width: 25, height: 250, value: start, theme: 'energyblue' });
          $('#jqxprogressbar').bind('valuechanged', function (event) {
              alert("Value: " + event.currentValue);
          });
          // Update ProgressBars.
          $('#button').click(function () {

            $.ajax({
                  type: 'GET',
                  url: '/Analisi',
                  data: {quarter: $("#quarter").val()},
                  dataType: 'json',
                  success: function (data) {

                    var obj = data;
                    for (var x in obj){
                      start = start + obj[x].Punteggio;
                      x++;



                    }
                    $('#chartContainer').jqxChart();
                    console.log(data);
                    console.log(start);
                    //console.log(nameValue);
                     $("#jqxprogressbar").jqxProgressBar({ value: ((start/document.getElementById("uniqueID").value)) * 100  });
                       //  console.log(start);
                         document.getElementById("resultpercentage").innerHTML = " Punteggio: " + start + " <br> Target: " + document.getElementById("uniqueID").value + " <br> Percentuale completamento: " + ((start/document.getElementById("uniqueID").value) * 100).toFixed([2]) + "% " ;
                        // console.log(document.getElementById("uniqueID").value);
                         console.log((start/document.getElementById("uniqueID").value) );
                         start = 0;
                  }
              });


          });
      });
