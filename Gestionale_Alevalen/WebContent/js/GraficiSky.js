
       $(document).ready(function () {
           $('#bottone').click(function () {
             var quarter = $("#quarter").val();
             $.ajax ({
               type: 'GET',
               data: {quarter: quarter},
               url: '/Analisi',
             
               success: function (response){
                 var punteggi = JSON.parse(response);





                     var source = {
                                 datatype: "json",
                                 datafields: [{
                                     name: 'Agente',
                                     type: 'string'
                                 }, {
                                     name: 'Punteggio',
                                     type: 'string'
                                 }],

                                 localdata: punteggi,

                                // url: '/Analisi',
                                 async: true
                             };

                 console.log(source);
                     // prepare jqxChart settings

                  var dataAdapter = new $.jqx.dataAdapter(source);
                        var settings = {
                            title: "Punteggio agenti",
                            description: " Quarter",
                            padding: {
                                left: 5,
                                top: 5,
                                right: 15,
                                bottom: 5
                            },
                            titlePadding: {
                                left: 90,
                                top: 0,
                                right: 0,
                                bottom: 10
                            },
                            source: dataAdapter,
                            xAxis: {
                                dataField: 'Agente',
                                displayText: 'Agente',
                                gridLines: {
                                  gridLinesColor: '#aac6fa',
                                  gridLinesInterval: 1,
                                    visible: true
                                },
                                valuesOnTicks: false,

                                type: 'basic',
                                labels: {
                                    class: 'labels',
                                    angle: 90,
                                    formatFunction: function (value) {
                                        return value.replace(/\?/g, '');
                                    }
                                },
                                flip: false
                            },
                            colorScheme: 'scheme05',
                            seriesGroups: [{
                                type: 'column',
                                columnsGapPercent: 100,
                                seriesGapPercent: 0,
                                orientation: 'horizontal',
                                valueAxis: {
                                  gridLinesColor: '#aac6fa',
                                    minValue: 0,
                                    unitInterval: 10,
                                    description: 'Punteggio',
                                    flip: true
                                },
                                series: [{
                                    dataField: 'Punteggio',
                                    displayText: 'Punteggio'
                                }]
                            }]
                        };
                        $('#chartContainer').jqxChart(settings);
                        $('#chartContainer').jqxChart({backgroundColor:'#DAE7FE'});
                        $('#chartContainer').jqxChart({borderLineColor: '#aac6fa'});



                        // Second chart creation

                        var settings2 = {
                                title: "Punteggio agenti",
                                description: "Quarter",
                                padding: {
                                    left: 5,
                                    top: 5,
                                    right: 15,
                                    bottom: 5
                                },
                                titlePadding: {
                                    left: 90,
                                    top: 0,
                                    right: 0,
                                    bottom: 10
                                },
                                source: dataAdapter,
                                xAxis: {
                                    dataField: 'Agente',
                                    displayText: 'Agente',
                                    gridLines: {
                                        visible: true
                                    },
                                    valuesOnTicks: false,
                                    type: 'basic',
                                    labels: {
                                        class: 'labels',
                                        angle: 90,
                                        formatFunction: function (value) {
                                            return value.toString().replace(/\?/g, '');
                                        }
                                    },
                                    flip: false
                                },
                                colorScheme: 'scheme01',
                                seriesGroups: [{
                                    type: 'pie',
                                    columnsGapPercent: 30,
                                    seriesGapPercent: 0,
                                    orientation: 'horizontal',
                                    valueAxis: {
                                        minValue: 0,
                                        unitInterval: 10,
                                        description: 'Punteggio',
                                        flip: true
                                    },
                                    series: [{
                                        dataField: 'Punteggio',
                                        displayText: 'Agente'
                                    }]
                                }]
                            };

                        $('#chartContainer2').jqxChart(settings2);
                        $('#chartContainer2').jqxChart({backgroundColor:'#DAE7FE'});
                        $('#chartContainer2').jqxChart({borderLineColor: '#c2d3f2'});

               }


             });
           });

           });
   
