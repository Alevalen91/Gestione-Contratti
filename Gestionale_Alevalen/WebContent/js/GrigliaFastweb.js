$(document).ready(function () {


		
           $("#excelExport").jqxButton({
                 theme: 'energyblue'
             });

            $("#excelExport").click(function() {
                  $("#jqxgrid").jqxGrid('exportdata', 'xls', 'jqxGrid');
              });
		
	
          var source = {
              datatype: "json",
              datafields: [{
                  name: 'Account',
                  type: 'string'
              }, {
                  name: 'Nome',
                  type: 'string'
              },

              {
                name: 'Cognome',
                type : 'string'
              },

              {
                name: 'Contratto',
                type: 'string'
              },
              
                  {
                name: 'Attivazione',
                type: 'string'
              },

              {
                name: 'Indirizzo',
                type: 'string'
              },

              {
                name: 'CAP',
                type: 'string'
              },

              {
                name: 'Telefono',
                type: 'string'
              },
           
              
              {
                  name: 'Email',
                  type: 'string'
              },
           

              {
                name: 'Agente',
                type: 'string'
              },

              {
                name: 'Codice Agente',
                type: 'int'
              },

              {
                name: 'Giorno',
                type: 'string'
              },
              
                 {
                name: 'Note',
                type: 'string'
              },

              ],
              cache: false,

              id: 'Account',
              url: '/FastwebInserimenti',
              filter: function() {
                  // update the grid and send a request to the server.
                  $("#jqxgrid").jqxGrid('updatebounddata', 'filter');
              },

              sort: function() {
                  // update the grid and send a request to the server.
                  $("#jqxgrid").jqxGrid('updatebounddata', 'sort');
              },


             async: true,

              beforeprocessing: function(data) {
                  if (data != null && data.length > 0) {
                     source.totalrecords = data[0].totalRecords;
                  }
              }

          };

          var filterChanged = false;
          var dataadapter = new $.jqx.dataAdapter(source , {

      // remove the comment to debug
            //  formatData: function(data) {
      //	alert(JSON.stringify(data));
        //alert(data);
      //	console.log (data);

                 // return data;
              //},
              downloadComplete: function(data, status, xhr) {
                  if (!source.totalRecords) {
                        source.totalRecords = data.length;
                  }
              },
              loadError: function(xhr, status, error) {
                  throw new Error(error);
              }
          });




          $("#jqxgrid").jqxGrid({
              width: 1000,

              source: dataadapter,
              filterable: true,
              sortable: true,
              autoheight: true,
              theme: 'energyblue',
              pageable: true,
              pagesize: 10,
              pagesizeoptions: ['10', '20', '50'],
              virtualmode: true,
              rendergridrows: function(obj) {
                  return obj.data;
              },



              columns: [{
                  text: 'Account',
                  datafield: 'Account',
                  width: 100
              }, {
                  text: 'Nome',
                  datafield: 'Nome',
                  width: 200
              },
              {
                text: 'Cognome',
                datafield: 'Cognome',
                width:  200
              },

              {
                text: 'Contratto',
                datafield: 'Contratto',
                width:  100
              },
              
                  {
                text: 'Attivazione',
                datafield: 'Attivazione',
                width:  100
              },

              {
                text: 'Indirizzo',
                datafield: 'Indirizzo',
                width:  300
              },

              {
                text: 'CAP',
                datafield: 'CAP',
                width:  80
              },

              {
                text: 'Telefono',
                datafield: 'Telefono',
                width:  100
              },
              
                  {
                 text: 'Email',
                 datafield: 'Email',
                 width:  200
               },
           

              {
                  text: 'Agente',
                  datafield: 'Agente',
                  width: 100
              },

              {
                  text: 'Codice Agente',
                  datafield: 'Codice Agente',
                  width: 300
              },

              {
                  text: 'Giorno',
                  datafield: 'Giorno',
                  width: 50
              },
              
                {
                  text: 'Note',
                  datafield: 'Note',
                  width: 500
              },




              ]
          });
      });


      
