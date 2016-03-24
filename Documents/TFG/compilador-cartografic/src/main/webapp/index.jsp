<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=9">
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
	<title>Compilador Cartogr&agrave;fic</title>
	<!-- src -->
	<script src="./js/jquery-2.1.4.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/ie10-viewport-bug-workaround.js"></script>
	<script src="./js/config.js"></script>
	<script src="./js/jquery.validate.min.js"></script>
	<script src="./js/ol.js"></script>
	<script src="./js/funcions.js"></script>
	<script src="./js/jquery.backstretch.js"></script>
	<!-- rel -->
	<link rel="shortcut icon" href="./img/logo.ico">
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/estils.css">
	<link rel="stylesheet" href="./css/jumbotron-narrow.css">
	<link rel="stylesheet" href="./css/ol.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:bold" rel="stylesheet" type="text/css">
 	<link href="http://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container white-bg">
    <div class="container">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active" id="registre" onclick="clickButtonRegistrar()"><a id="registre-text" href="#">Registrar-se</a></li>
            <li role="presentation"><a href="#" id="entrar" onclick="clickButtonEntrar()">Entrar</a></li>
            <li class="dropdown" id="idioma">
			  <button class="btn btn-default dropdown-toggle ui-idioma-boto-ppal" onclick="myClickFunction3()" type="button" id="idioma-menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    Idioma
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="idioma-menu">
			    <li>
			    	<a href="#" class="ui-idioma-boto" onclick="translate2Catalan()" id="idioma-catala">CA
			    	<img src="./img/catalan_flag.png" class="ui-img-idioma"></a>
			    </li>
			    <li role="separator" class="divider"></li>
			    <li>
			    	<a href="#" class="ui-idioma-boto" onclick="translate2Spanish()" id="idioma-castellano">ES
			    	<img src="./img/spain.png" class="ui-img-idioma"></a>
			    </li>
			  </ul>
			</li>
          </ul>
        </nav>
        <h3 class="text-muted ui-title" id="titol">Compilador Cartogr&agrave;fic</h3>
      </div>
      <div class="text-center ui-entrar-formulari" id="entrar-formulari">
      	<div class="logo ui-entrar-text" id="entrar-text">Entrar
      	<button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="closeEntrarForm()"><span aria-hidden="true">&times;</span></button>
      	</div>
<!--       	<div class="ui-arrow-up"></div> -->
<!-- 			<img class="ui-icona-entrar" src="./img/pestanya.png" id="icona-entrar"></img> -->
      		<div class="login-form-1">
      			<form id="login-form" class="text-left" onsubmit="return enterValidation()">
      				<div class="login-form-main-message"></div>
						<div class="main-login-form">
							<div class="login-group">
								<div class="form-group">
									<label for="lg_username" class="sr-only control-label" id="entrar-nom-de-usuari">Username</label>
									<input type="text" class="form-control" id="lg_username" name="lg_username" placeholder="Username">
								</div>
								<div class="form-group">
									<label for="lg_password" class="sr-only" id="entrar-contrasenya">Password</label>
									<input type="password" class="form-control" id="lg_password" name="lg_password" placeholder="Password">
								</div>
								<p class="ui-user-login-text" id="user-login-text"></p>
							</div>
							<button type="submit" class="login-button ui-entrar-formulari-boto">Login<i class="fa fa-chevron-right"></i></button>
						</div>
				</form>
			</div>
		</div>
		<div class="text-center ui-registre-formulari" id="registre-formulari">
      	<div class="logo ui-formulari-registre-text" id="registre-formulari-text">Registre
      	<button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="closeRegistrarseForm()"><span aria-hidden="true">&times;</span></button>
      	</div>
<!--       		<img class="ui-icona-registrar" src="./img/pestanya.png" id="icona-registrar"></img> -->
      		<div class="login-form-1">
      			<form id="register-form" class="text-left"  onsubmit="return registerValidation()">
      				<div class="login-form-main-message"></div>
						<div class="main-login-form">
							<div class="login-group">
						<div class="form-group">
							<input type="text" class="form-control" id="rg_username" name="lg_username" placeholder="Username">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="rg_client" name="lg_client" placeholder="Client">
						</div>
						<div class="form-group">
							<label for="lg_password" class="sr-only" id="registre-contrasenya">Password</label>
							<input type="password" class="form-control" id="rg_password" name="lg_password" placeholder="Password">
						</div>
						<div class="form-group">
							<label for="lg_email" class="sr-only" id="registre-email">Email</label>
							<input type="text" class="form-control" id="rg_email" name="lg_email" placeholder="Email">
						</div>
						<div class="form-group">
							<label for="lg_rol" class="sr-only" id="registre-contrasenya">Rol</label>
							<input type="text" class="form-control" id="rg_rol" name="lg_rol" placeholder="Role">
						</div>
						<p id="missatge-registre" class="ui-missatge-registre"></p>
					</div>
					<button type="submit" class="login-button ui-registre-formulari-boto">Sign In<i class="fa fa-chevron-right"></i></button>
						</div>
				</form>
			</div>
		</div>
	  <div class="container ui-container-online">
	  	<img class="ui-icona-persona" src="./img/offline.png" id="icona-persona"></img>
	  	<p class="ui-nom-persona" id="nom-persona">Offline</p>
	  </div>
	  <div class="text-center ui-file-upload-container2" id="file-upload-container2"><b>Importa els contactes</b>
        <form class="text-center ui-boto-pujar-fitxer" method="POST" enctype="multipart/form-data">
			<input type="file" name="fileUploader2" id="fileUploader2" class="ui-boto-pujar-fitxer"/>
	  	</form>
	  </div>
      <div class="jumbotron">
      	<h2 class="ui-opcio-a" id="opcio-a">M&egrave;tode Principal</h2>
<!--         <h3 class="ui-importacio" id="importacio">ImportaciÃ³</h3> -->
        <p class="lead ui-importacio-text" id="importacio-text">Selecciona un fitxer (formats: SHP, OSM, KML o CSV)</p>
        <div class="text-center ui-file-upload-container">
        <form class="text-center ui-boto-pujar-fitxer" method="POST" enctype="multipart/form-data">
			<input type="file" name="fileUploader" id="fileUploader" class="ui-boto-pujar-fitxer"/>
	  	</form>
	  	</div>
	  	<p class="ui-puja-fitxer" id="puja-fitxer"></p>
	  	<div class="container ui-container-table" id="container-table">
   		 <div class="row clearfix">
			<div class="col-md-12 column">
				<table class="table table-bordered table-hover ui-table-files" id="tab_logic">
					<thead>
						<tr>
							<th class="text-center">#</th>
							<th class="text-center" id="taula-atribut2">Fitxer</th>
							<th class="text-center">Hora</th>
							<th class="text-center" id="taula-atribut4">Selecci&oacute;</th>
							<th class="text-center" id="taula-atribut5">Columnes (#Files)</th>
						</tr>
					</thead>
					<tbody id="tbody-table">
<!-- 						<tr id='addr0'></tr> -->
<!-- 	                    <tr id='addr1'></tr> -->
					</tbody>
				</table>
			</div>
		</div>
	  	</div>
	  	<p class="ui-user-login-text" id="file-change-text"></p>
	  	<div class="row-fluid">
		  	<button type="button" class="btn btn-sq-xs btn-primary btn-square ui-desfer-canvi-boto" id="desfer-canvi-boto" onClick="undoChange2File()">
		  		<img src="./img/undo.png" title="Desfer canvi" width="30"/>
		  	</button>
		  	<button type="button" class="btn btn-sq-xs btn-primary ui-aplicar-canvi-boto" data-toggle="modal" data-target="#myModal">
		  		<img src="./img/edit.png" title="Modificar fitxer" width="30"/>
		  	</button>
		  	<button type="button" class="btn btn-sq-xs btn-primary btn-square ui-exportar-boto" id="exportar-boto1" role="button" onclick="exportImportedFile()">
      			<img src="./img/export.png" title="Exportar .PY" width="30"/>
      		</button>
      	</div>
      	<p class="ui-exporta-fitxer" id="exporta-fitxer"></p>
      </div>
      <!-- Modal -->
      <div class="modal fade" id="myModal" role="dialog">
       <div class="modal-dialog"> 
      <!-- Modal content-->
        <div class="modal-content">
         <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><b>Aplicar Canvi</b></h4>
         </div>
         <div class="modal-body">
          <p>Tria el canvi a realitzar en el fitxer:</p>
            <form role="form">
			    <div class="radio">
			      <label><input type="radio" onClick="enableCoordinates()" id="radioCoordenades" name="optradio">Coordenades</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentCoordenades" placeholder="EPSG:4326" disabled></textarea>
			    </div>
			    <div class="radio">
			      <label><input type="radio" onClick="enableStyle()" id="radioEstil" name="optradio" disabled>Estil</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentEstil" placeholder="street=blue" disabled></textarea>
			    </div>
			    <div class="radio">
			      <label><input type="radio" onClick="enableFilter()" id="radioFiltre" name="optradio">Filtre</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentFiltre" placeholder="rows=20, columns=columnfield(s), rows=20;columns=columnfield(s), columnfield=value" disabled></textarea>
			    </div>
			    <div class="radio">
			      <label><input type="radio" onClick="enableDelete()" id="radioEliminar" name="optradio">Eliminar</label>
			    </div>
		  	</form>
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="applyChange2File()" data-dismiss="modal">Aplicar</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>  
    </div>
  	</div>
      <div class="jumbotron">
      	<div class="container">
      	<button class="ui-option-method-button pull-right" id="option-method-button" onclick="clickOptionalMethodButton()"><p id="option-method-button-text" class="ui-option-method-button-text"></p></button>
        <h2 class="ui-opcio-b" id="opcio-b">M&egrave;tode Opcional</h2>
<!--         <h3 class="ui-modificacio" id="modificacio">ImportaciÃ³</h3> -->
		</div>
		<div class="ui-option-method container" id="option-method">
        <p class="lead ui-importacio-text" id="importacio-text2">Selecciona una porci&oacute; del mapa:</p>
		<div class="row-fluid">
	  		<div class="span12">
	    	<div id="map" class="ui-map"></div>
	  		</div>
		</div>
    	<button type="button" class="btn btn-sq-xs btn-primary ui-exportar-boto2" id="exportar-boto" role="button" onclick="myClickFunction6b()">
    		<img src="./img/export.png" title="Exportar .MAP" width="30"/>
    	</button>
    	<div id="myProgress" class="ui-myProgress"><div id="myBar" class="ui-myBar"></div></div>
    	<p class="ui-exporta-fitxer-mapa" id="exporta-fitxer-mapa"></p>
    </div>
    </div>
        <footer class="footer">
    	<p>&copy; 2015-2016 SITEP, Inc.</p>
    </footer>
    </div>
    <!-- /container -->
    <script>
    $.backstretch("./img/globe.jpg");
	function clickButtonRegistrar() {
		console.log("registre");
		$("#registre-formulari").show();
		$("#entrar-formulari").hide();
	}; 
	function clickButtonEntrar() {
		++entryVariable;
		console.log("entrar (clickButtonEntrar): " +entryVariable);
		if ($("#entrar").text().indexOf("Log out") == 0) {
			$("#tbody-table").empty();
			$("#entrar").text("Entrar");
			$("#puja-fitxer").text("");
			$("#file-change-text").text("");
			$("#lg_username").text("");
			$("#lg_password").text("");
			entryVariable = 0;
			$("#icona-persona").attr("src","./img/offline.png");
			$("#nom-persona").text("Offline");
			$("#file-upload-container2").hide();
		}
		else {
			$("#entrar-formulari").show();
			$("#registre-formulari").hide();
		}
	}; 
	/*function myClickFunction3() {
		console.log("idioma");
	};*/
	function exportImportedFile() {
		console.log("exportar-boto1");
		var howManyCheckboxesChecked = 0;
		var numberOfFile = null;
		for (var i = 1; i <= filesUploaded; i++) {
			if ($("#checkbox-file" + i).prop('checked') == true) {
				++howManyCheckboxesChecked;
				numberOfFile = i;
			}
		}
		if (howManyCheckboxesChecked < 1) {
			$("#exporta-fitxer").text("No s'ha seleccionat cap fitxer!");
        	$("#exporta-fitxer").css("color", "red");
        	$("#exporta-fitxer").css("font-size", "13px");
		}
		else if (howManyCheckboxesChecked == 1) {
			var fileNameString = $("#file-name" + numberOfFile).text();
			var extension = $("#file-name" + numberOfFile).text().split(".")[1];
			var fileNameStringEmpty = "";
			console.log(fileNameString.valueOf() + " ==? " + fileNameStringEmpty.valueOf());
			console.log(fileNameString.valueOf() == fileNameStringEmpty.valueOf());
			if (fileNameString.valueOf() == fileNameStringEmpty.valueOf()) {
				$("#exporta-fitxer").text("S'ha seleccionat una columna buida!");
	        	$("#exporta-fitxer").css("color", "red");
	        	$("#exporta-fitxer").css("font-size", "13px");
			}
			else {
				window.open("http://localhost/compilador-cartografic/FormatServiceController?filename="+fileNameString+"&username="+userSession+"&extension="+extension+"&function=ly")
				$("#exporta-fitxer").text("La descarrega comensara en breus...");
	        	$("#exporta-fitxer").css("color", "green");
	        	$("#exporta-fitxer").css("font-size", "13px");
			}
		}
		else {
			$("#exporta-fitxer").text("S'ha seleccionat mes d'un fitxer a la vegada!");
        	$("#exporta-fitxer").css("color", "red");
        	$("#exporta-fitxer").css("font-size", "13px");
		}
	};
	function move() {
		  var elem = document.getElementById("myBar");     
		  var width = 0;
		  function frame() {
		    if (width == 100) width = 0;
		    else {
		      width++; 
		      elem.style.width = width + '%';
		    }
	  	}
	};
	function myClickFunction6b() {
		$("#myProgress").show();
		move();
		console.log("exportar-boto");
		var extent = map.getView().calculateExtent(map.getSize());
		extent = ol.proj.transformExtent(extent, 'EPSG:3857', 'EPSG:4326');
		var boundingBoxString = "";
		for (var i = 0; i < extent.length; i++) {
			if (i == (extent.length - 1)) boundingBoxString += extent[i];
			else boundingBoxString += extent[i] + ",";
		}
		$.ajax({
			url: 'http://localhost/compilador-cartografic/FormatServiceController?function=bg',
	        type: 'POST',
	        data: boundingBoxString,
	        success: function(data, textStatus, jqXHR) {
	        	$("#myProgress").hide();
	        	window.open("http://localhost/compilador-cartografic/FormatServiceController?function=bg", "FonsServiceController");
	        	$("#exporta-fitxer-mapa").css("color", "green");
	        	$("#exporta-fitxer-mapa").text("La desc&agrave;rrega s'inicialitzar&agrave; en breus...");
	        	for (i = 0; i <= 100; i++) 
	        		if (i == 10) $("#exporta-fitxer-mapa").text(""); // Mes temps
	        	$("#exporta-fitxer-mapa").css("font-size", "13px");
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	        	$("#myProgress").hide();
	        	$("#exporta-fitxer-mapa").css("color", "red");
	        	$("#exporta-fitxer-mapa").text("La desc&agrave;rrega ha tingut un problema!");
	        	$("#exporta-fitxer-mapa").css("font-size", "13px");
	        }
	     });
	};
	function translate2Catalan() {
    	try {
			console.log("editIdioma");
			$.ajax({
				url: 'http://localhost/compilador-cartografic/IdiomaServiceController?info=edit&idioma=encatala',
			       type: 'POST',
			       success: function(data, textStatus, jqXHR) {
			    	   console.log("IdiomaServiceController (edit) success");
			    	   },
			    	   error: function(jqXHR, textStatus, errorThrown) {
			    		   console.log("IdiomaServiceController (edit) error");
			    		   }
			    	   });
		} catch (e) { throw new Error(e.message); }
		idiomaVariable = 0;
		console.log("idioma-catala");
		$("#titol").text("Compilador Cartografic");
		$("#opcio-a").text("Metode Principal");
		$("#opcio-b").text("Metode Opcional");
		$("#importacio-text2").text("Selecciona una porcio del mapa:");
		$("#taula-atribut2").text("Fitxer");
		$("#taula-atribut4").text("Seleccio");
		$("#registre-text").text("Registrar-se");
		$("#importacio").text("Importacio");
		$("#importacio-text").text("Selecciona un fitxer (formats: SHP, OSM, KML o CSV)");
		$("#modificacio").text("Modificacio");
		$("#modificacio-text").text("Coordenades, estil, format y filtre.");
		$("#canvi").text("Canvis");
		$("#canvi-opcio").text("Tipus");
		$("#coordenades").text("Coordenades");
		$("#estils").text("Estils");
		$("#formats").text("Formats");
		$("#filtres").text("Filtres");
		$("#modificacio-boto").text("Aplicar canvi");
		$("#registre-formulari-text").text("Registre");
	}; 
	function translate2Spanish() {
    	try {
			console.log("editIdioma");
			$.ajax({
				url: 'http://localhost/compilador-cartografic/IdiomaServiceController?info=edit&idioma=encastella',
			       type: 'POST',
			       success: function(data, textStatus, jqXHR) {
			    	   console.log("IdiomaServiceController (edit) success");
			    	   },
			    	   error: function(jqXHR, textStatus, errorThrown) {
			    		   console.log("IdiomaServiceController (edit) error");
			    		   }
			    	   });
		} catch (e) { throw new Error(e.message); }
		idiomaVariable = 1;
		console.log("idioma-castella");
		$("#titol").text("Compilador Cartografico");
		$("#opcio-a").text("Metodo Principal");
		$("#opcio-b").text("Metodo Opcional");
		$("#importacio-text2").text("Selecciona una porcion del mapa:");
		$("#taula-atribut2").text("Fichero");
		$("#taula-atribut4").text("Seleccion");
		$("#registre-text").text("Registrarse");
		$("#importacio").text("Importacion");
		$("#importacio-text").text("Selecciona un fichero (formatos: SHP, OSM, KML o CSV)");
		$("#modificacio").text("Modificacion");
		$("#modificacio-text").text("Coordenadas, estilo, formato y filtro.");
		$("#canvi").text("Cambios");
		$("#canvi-opcio").text("Tipos");
		$("#coordenades").text("Coordenadas");
		$("#estils").text("Estilos");
		$("#formats").text("Formatos");
		$("#filtres").text("Filtros");
		$("#modificacio-boto").text("Aplicar cambio");
		$("#registre-formulari-text").text("Registro");
	};
	function closeEntrarForm() {
		console.log("entrar-close");
		$("#entrar-formulari").hide();
		$("#user-login-text").text("");
	};
	function closeRegistrarseForm() {
		console.log("registre-close");
		$("#registre-formulari").hide();
		$("#missatge-registre").text("");
	};
	function hideEntrarForm() {
		$("#entrar-formulari").hide();
	};
	function enableCoordinates() {
		$("#commentCoordenades").attr("disabled", false); 
		$("#commentEstil").attr("disabled", true);
		$("#commentFiltre").attr("disabled", true);
	};
	function enableStyle() {
		$("#commentCoordenades").attr("disabled", true); 
		$("#commentEstil").attr("disabled", false);
		$("#commentFiltre").attr("disabled", true);
	};
	function enableFilter() {
		$("#commentCoordenades").attr("disabled", true); 
		$("#commentEstil").attr("disabled", true);
		$("#commentFiltre").attr("disabled", false);
	};
	function undoChange2File() {
		undoActivated = true;
		optionChange = "desfer";
		optionText = "buit";
		applyChange2File();
	};
	function applyChange2File() {
		if (!undoActivated) {
			if ($('#radioCoordenades').is(":checked")) {
				optionChange = "coord";
				optionText = $("#commentCoordenades").val();
			}
			else if ($('#radioEstil').is(":checked")) {
				optionChange = "estil";
				optionText = $("#commentEstil").val();
			}
			else if ($('#radioFiltre').is(":checked")) {
				optionChange = "filtre";
				optionText = $("#commentFiltre").val();
			}
			else if ($('#radioEliminar').is(":checked")) {
				optionChange = "eliminar";
				optionText = "buit";			
			}
			/*else if (undoActivated == true) {
				optionChange = "desfer";
				optionText = "buit";
				undoActivated = false;
			}*/
		}
		var howManyCheckboxesChecked = 0;
		var numberOfFile = null;
		for (var i = 1; i <= filesUploaded; i++) {
			if ($("#checkbox-file" + i).prop('checked') == true) {
				++howManyCheckboxesChecked;
				numberOfFile = i;
			}
		}
		if (howManyCheckboxesChecked < 1) {
			$("#exporta-fitxer").text("No s'ha seleccionat cap fitxer!");
        	$("#exporta-fitxer").css("color", "red");
        	$("#exporta-fitxer").css("font-size", "13px");
		}
		else if (howManyCheckboxesChecked == 1) {
			var fileNameString = $("#file-name" + numberOfFile).text();
			var fileNameStringEmpty = "";
			console.log(fileNameString.valueOf() + " ==? " + fileNameStringEmpty.valueOf());
			console.log(fileNameString.valueOf() == fileNameStringEmpty.valueOf());
			if (fileNameString.valueOf() == fileNameStringEmpty.valueOf()) {
				$("#exporta-fitxer").text("S'ha seleccionat una columna buida!");
	        	$("#exporta-fitxer").css("color", "red");
	        	$("#exporta-fitxer").css("font-size", "13px");
			}
			else {
				try {
					$.ajax({
						url: 'http://localhost/compilador-cartografic/AplicarCanviServiceController?name='+fileNameString+'&user='+userSession+'&info='+optionText+'&c='+optionChange,
						type: 'POST',
// 						data: '',
						success: function(data, textStatus, jqXHR) {
							console.log("UsuariServiceController success " + data);
							if (optionChange.indexOf("coord") == 0) {
								if (data.indexOf("SI") == 0) {
									$("#file-change-text").text("El canvi de coordenades s'ha realitzat correctament.");
						        	$("#file-change-text").css("color", "green");
						        	$("#file-change-text").css("font-size", "13px");
									}
								}
							else if (optionChange.indexOf("filtre") == 0) {
								$("#file-change-text").text("El filtre s'ha aplicat correctament.");
					        	$("#file-change-text").css("color", "green");
					        	$("#file-change-text").css("font-size", "13px");
								$("#file-filter" + (numberOfFile)).text(data);
								}
							else if (optionChange.indexOf("eliminar") == 0) {
								deleteRow(numberOfFile);
								$("#file-change-text").text("El fitxer s'ha eliminat correctament.");
					        	$("#file-change-text").css("color", "green");
					        	$("#file-change-text").css("font-size", "13px");
								}
							else if (optionChange.indexOf("desfer") == 0) {
								$("#file-change-text").text("S'ha desfet el canvi del fitxer correctament.");
					        	$("#file-change-text").css("color", "green");
					        	$("#file-change-text").css("font-size", "13px");
					        	if (data.indexOf("NO") == -1) $("#file-filter" + (numberOfFile)).text(data);
								}
							},
							error: function(jqXHR, textStatus, errorThrown) {
								console.log("AplicarCanviServiceServle error" + data);
								$("#file-change-text").css("color", "red");
								if (optionChange.indexOf("coord") == 0)
									$("#file-change-text").text("Les coordenades introduides no permeten realitzar el canvi.");
// 								else if (optionChange.indexOf("estil") == 0)
// 									$("#file-change-text").text("L'estil introduit no permet realitzar el canvi.");
								else if (optionChange.indexOf("filtre") == 0)
									$("#file-change-text").text("El filtre introduit no permet realitzar el canvi.");
								else if (optionChange.indexOf("eliminar") == 0)
									$("#file-change-text").text("No es pot eliminar el fitxer.");			
								else if (optionChange.indexOf("desfer") == 0)
									$("#file-change-text").text("No es pot desfer el canvi.");
								$("#file-change-text").css("font-size", "13px");	
								}
							});
					}
				catch (e) { throw new Error(e.message); }
			}
		}
		else {
			$("#exporta-fitxer").text("S'ha seleccionat mes d'un fitxer a la vegada!");
        	$("#exporta-fitxer").css("color", "red");
        	$("#exporta-fitxer").css("font-size", "13px");
		}
	};
	function clickOptionalMethodButton() {
		var stringOfImage = $("#option-method-button").css('background-image');
		if (stringOfImage.search("arrow-down.png") != -1) {
			var imgUrl = './img/arrow-up.png';
			$("#option-method-button").css('background-image', 'url(' + imgUrl + ')');
// 			$("#option-method-button-text").text("Replegar");
			$("#option-method").show();
		}
		else {
			var imgUrl = './img/arrow-down.png';
			$("#option-method-button").css('background-image', 'url(' + imgUrl + ')');
// 			$("#option-method-button-text").text("Desplegar");
			$("#option-method").hide();
		}
	};
	function enableDelete() {
		$("#commentCoordenades").attr("disabled", true); 
		$("#commentEstil").attr("disabled", true);
		$("#commentFiltre").attr("disabled", true);
	};
	function uploadFile(event) {
// 		alert("HOLA");
		event.stopPropagation(); 
		event.preventDefault(); 
		var files = event.target.files; 
		var data = new FormData();
		file2uploadName = files[0].name;
		$.each(files, function(key, value) {
			data.append(key, value);
		});
		postFilesData(data); 
	};
	
	function uploadClients(event) {
		alert("uploadClients");
		event.stopPropagation(); 
		event.preventDefault(); 
		var files = event.target.files; 
		var data = new FormData();
		file2uploadName = files[0].name;
		$.each(files, function(key, value) {
			data.append(key, value);
		});
		console.log("uploadClients data: " + data);
		postClientsData(data); 
	};
	
	function postFilesData(data)
	{
		console.log("postFilesData");
		$.ajax({
			url: 'http://localhost/compilador-cartografic/FitxerServiceController?name='+userSession, //+'&filename='+file2uploadName
	        type: 'POST',
	        data: data,
	        cache: false,
	        dataType: 'json',
	        processData: false, 
	        contentType: false, 
	        success: function(data, textStatus, jqXHR) {
	        	$("#fileUploader").val("");
	        	DateTime();
	        	$("#puja-fitxer").css("color", "green");
	        	$("#puja-fitxer").text("El fitxer " + file2uploadName + " s'ha pujat correctament.");
	        	$("#puja-fitxer").css("font-size", "13px");
	        	if (data == 1) {
	        		console.log("data == 1");
	        		filesUploaded = 1;
	        		for (var i = 0; i <= nombreDeFiles; i++) {
	                	$('#addr'+i).html("<td>"+ i +"</td><td class='bk-td' id='file-name"+i+"'>" + "" + "</td><td class='bk-td' id='file-hour"+i+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+i+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+i+"'>" + "" + "</td>");
	                    $('#tab_logic').append('<tr id="addr' + (i + 1) + '"></tr>');
// 	                    if (i == 1) {
// 	                    }
	        		}
                	if (file2uploadName.indexOf("zip") > -1) file2uploadName = file2uploadName.replace("zip", "shp");
					if ((file2uploadName.indexOf("osm") > -1)) {
						var type = "";
	        			for (var i = 0; i < 4; i++) {
		    		    	if (i == 0) type = "line";
		    		    	else if (i == 1) type = "point";
		    		    	else if (i == 2) type = "polygon";
		    		    	else type = "roads";
	        				$("#file-name" + filesUploaded).text(file2uploadName.split(".")[0] + 
	        						type + ".shp");
			        		$("#file-hour" + filesUploaded).text(today);
			        		++filesUploaded;
	        			}
	        		}
	        		else {
	                	$("#file-name" + filesUploaded).text(file2uploadName);
		        		$("#file-hour" + filesUploaded).text(today);
	        		}
	        	}
	        	else if (data > 1) {
	        		console.log("data == " + data);
	        		filesUploaded = data;
	        		$("#file-filter" + filesUploaded).html('<img src="./img/loading.gif"> loading...');
	        		$("#file-filter" + filesUploaded+1).html('<img src="./img/loading.gif"> loading...');
	        		$("#file-filter" + filesUploaded+2).html('<img src="./img/loading.gif"> loading...');
	        		$("#file-filter" + filesUploaded+3).html('<img src="./img/loading.gif"> loading...');
	        		if (file2uploadName.indexOf("zip") > -1) file2uploadName = file2uploadName.replace("zip", "shp");
	        		if ((file2uploadName.indexOf("osm") > -1)) {
	        			for (var i = 0; i < 4; i++) {
		    		    	if (i == 0) type = "line";
		    		    	else if (i == 1) type = "point";
		    		    	else if (i == 2) type = "polygon";
		    		    	else type = "roads";
	        				$("#file-name" + filesUploaded).text(file2uploadName.split(".")[0] + 
	        						type + ".shp");
			        		$("#file-hour" + filesUploaded).text(today);
	        				++filesUploaded;
	        			}
	        		}
	        		else {
		        		$("#file-name" + filesUploaded).text(file2uploadName);
		        		$("#file-hour" + filesUploaded).text(today);
	        		}
	        	}
	        	/*if (filesUploaded >= 1 && filesUploaded <= nombreDeFiles) {
	        		$("#file-name" + filesUploaded).text(file2uploadName);
	        		$("#file-hour" + filesUploaded).text(today);
	        	}
	        	else {
	        	console.log("success postFilesData: " + filesUploaded);
	            $('#addr'+filesUploaded).html("<td>"+ filesUploaded +"</td><td class='bk-td' id='file-name"+filesUploaded+"'>" + "" + "</td><td class='bk-td' id='file-hour"+filesUploaded+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+filesUploaded+"'>" + "" + "</td>");
	            $('#tab_logic').append('<tr id="addr' + (filesUploaded) + '"></tr>');
	            $('#addr'+filesUploaded).html("<td>"+ filesUploaded +"</td><td class='bk-td' id='file-name"+filesUploaded+"'>" + "" + "</td><td class='bk-td' id='file-hour"+filesUploaded+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+filesUploaded+"'>" + "" + "</td>");
                $('#tab_logic').append('<tr id="addr' + (filesUploaded) + '"></tr>');
                $("#file-name" + filesUploaded).text(file2uploadName);
	        	$("#file-hour" + filesUploaded).text(today);
	        		$('#addr'+filesUploaded).html("<td>"+ (filesUploaded+1) +"</td><td class='bk-td'>" + file2uploadName + "</td><td class='bk-td'>" + today + "</td><td class='bk-td'><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+filesUploaded+"'>" + "" + "</td>");
		            $('#tab_logic').append('<tr id="addr' + (filesUploaded + 1) + '"></tr>');
	        	}
	        	filesUploaded++;*/
	        	uploadToDatabase(file2uploadName);
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	        	$("#fileUploader").val("");
	        	console.log('ERRORS: ' + textStatus);
	        	$("#puja-fitxer").css("color", "green"); // red
	        	$("#puja-fitxer").text("El fitxer " + file2uploadName + " s'ha pujat correctament.");
	        	$("#puja-fitxer").css("font-size", "13px");
	        }
	     });
	};
	function postClientsData(data)
	{
		console.log("postFilesData");
		$.ajax({
			url: 'http://localhost/compilador-cartografic/ClientServiceController?name='+userSession, //+'&filename='+file2uploadName
	        type: 'POST',
	        data: data,
	        cache: false,
	        dataType: 'json',
	        processData: false, 
	        contentType: false, 
	        success: function(data, textStatus, jqXHR) {
	        	console.log("Clients pujats");
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	        	console.log("Clients pujats amb error")
	        }
	     });
	};
	function uploadToDatabase(filename) {
		$.ajax({
			url: 'http://localhost/compilador-cartografic/FormatServiceController?user='+userSession+'&info=1&function=vc',
	        type: 'POST',
	        data: filename,
	        success: function(data, textStatus, jqXHR) {
				console.log("uploadToDatabase success filesUploaded: " + filesUploaded);
				if (data.replace(/[^)]/g, "").length > 1) {
					var dataVector = data.split(")");
					var count = -4;
					for(var i = 0; i < dataVector.length-1; i++) {
						$("#file-filter" + (filesUploaded + count)).text(dataVector[i] +")");
						++count;
					}
					/*uploadOsmFiles(filename);*/
				}
				else
					$("#file-filter" + filesUploaded).text(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log("uploadToDatabase error");
					}
				});
	};
	function uploadOsmFiles(filename) {
		$.ajax({
			url: 'http://localhost/compilador-cartografic/FormatServiceController?user='+userSession+"&info=osm",
	        type: 'POST',
	        data: filename,
	        success: function(data, textStatus, jqXHR) {
				console.log("uploadOsmFiles success osmFilesUploaded: " + filesUploaded);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log("uploadOsmFiles error");
					}
				});
	};
	$('#fileUploader').on('change', uploadFile);
	$('#fileUploader2').on('change', uploadClients);
	$("#entrar-formulari").hide();
	$("#registre-formulari").hide();
	$("#myProgress").hide();
	$("#file-upload-container2").hide();
	function enterValidation(evt) {
		try {
			userSession = $("#lg_username").val();
			console.log("Nom: " + $("#lg_username").val() + ", Pass: " + $("#lg_password").val());
			var userInformation = "1," + $("#lg_username").val() + "," + $("#lg_password").val();
			console.log("userInformation: " + userInformation);
			$.ajax({
				url: 'http://localhost/compilador-cartografic/UsuariServiceController?userSession='+userSession,
				type: 'POST',
				data: userInformation,
				success: function(data, textStatus, jqXHR) {
					console.log("UsuariServiceController success " + data);
					if (data.indexOf("NO") == -1) {
						$("#lg_username").val("");
						$("#lg_password").val("");
						$("#user-login-text").text("");
						hideEntrarForm();
						++entryVariable;
						console.log("entrar (hideEntrarForm): " +entryVariable);
						$("#entrar").text("Log out");
						alert(data);
						$("#icona-persona").attr("src","./img/online.png");
						$("#nom-persona").text(userSession);
						if (data.indexOf(";") > -1) {
			        		for (var i = 0; i <= nombreDeFiles; i++) {
			                	$('#addr'+i).html("<td>"+ i +"</td><td class='bk-td' id='file-name"+i+"'>" + "" + "</td><td class='bk-td' id='file-hour"+i+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+i+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+i+"'>" + "" + "</td>");
			                    $('#tab_logic').append('<tr id="addr' + (i + 1) + '"></tr>');
			        		}
// 							var repeats = data.match(/;/gi).length;
							var dataVector = data.split(';');
							for(var i = 0; i < dataVector.length; i++) {
								var dataVectorRow = dataVector[i].split("'");
					        	/*if (filesUploaded >= 1 && filesUploaded <= dataVectorRow[3]) {*/
	            				filesUploaded = i + 1;
					        	/*$('#addr'+filesUploaded).html("<td>"+ filesUploaded +"</td><td class='bk-td' id='file-name"+filesUploaded+"'>" + "" + "</td><td class='bk-td' id='file-hour"+filesUploaded+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+filesUploaded+"'>" + "" + "</td>");
                				$('#tab_logic').append('<tr id="addr' + (filesUploaded) + '"></tr>');*/
                                $("#file-name" + filesUploaded).text(dataVectorRow[0]);
                	        	$("#file-hour" + filesUploaded).text(dataVectorRow[1]);
                				$("#file-filter" + filesUploaded).text(dataVectorRow[2]);
					        		/*++filesUploaded;
					        	}*/
							}
						}
						else if (data.indexOf("'") > -1) {
			        		for (var i = 0; i <= nombreDeFiles; i++) {
			                	$('#addr'+i).html("<td>"+ i +"</td><td class='bk-td' id='file-name"+i+"'>" + "" + "</td><td class='bk-td' id='file-hour"+i+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+i+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+i+"'>" + "" + "</td>");
			                    $('#tab_logic').append('<tr id="addr' + (i + 1) + '"></tr>');
			        		}
							var dataVectorRow = data.split("'");
					        /*if (filesUploaded >= 1 && filesUploaded <= dataVectorRow[3]) {*/
						    filesUploaded = 1;
					        /*$('#addr'+filesUploaded).html("<td>"+ filesUploaded +"</td><td class='bk-td' id='file-name"+filesUploaded+"'>" + "" + "</td><td class='bk-td' id='file-hour"+filesUploaded+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+filesUploaded+"'>" + "" + "</td>");
				            $('#tab_logic').append('<tr id="addr' + (filesUploaded) + '"></tr>');*/					        	$("#file-name0").text(dataVectorRow[0]);
                            $("#file-name" + filesUploaded).text(dataVectorRow[0]);
                	        $("#file-hour" + filesUploaded).text(dataVectorRow[1]);
                			$("#file-filter" + filesUploaded).text(dataVectorRow[2]);
					        /*++filesUploaded;
					        }*/
						}
						$("#user-login-text").text("");
						if (userSession.indexOf("admin") == 0) $("#file-upload-container2").show();
						return true;
						}
					else {
						$("#user-login-text").css("color", "red");
						$("#user-login-text").text("Les dades introduides no son correctes.");
						$("#user-login-text").css("font-size", "13px");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log("UsuariServiceController error" + data);
						}
					});
			}
		catch (e) { throw new Error(e.message); }
		return false;
	};
	function registerValidation(evt) {
		try {
			console.log("Nom: " + $("#rg_username").val() + ", Pass: " + $("#rg_password").val() + ", Email: " +
				$("#rg_email").val() + ", Rol: " + $("#rg_rol").val() + ", Client: " + $("#rg_client").val());
			var userInformation = "0," + $("#rg_username").val() + "," + $("#rg_password").val() + "," + 
				$("#rg_email").val() + "," + $("#rg_rol").val() + "," + $("#rg_client").val();
			console.log("userInformation: " + userInformation);
			$.ajax({
				url: 'http://localhost/compilador-cartografic/UsuariServiceController',
			       type: 'POST',
			       data: userInformation,
			       success: function(data, textStatus, jqXHR) {
			    	   console.log("UsuariServiceController success");
			    	   if (data.indexOf("1") == 0) {
			    		   console.log("data.indexOf(1) == 0");
			    		   $("#missatge-registre").text("");
			    		   $("#registre-formulari").hide();
			    		   return true;
			    	   }
			    	   else {
			    		   if (data.indexOf("2") == 0) {
			    			   console.log("data.indexOf(2) == 0");
			    			   $("#missatge-registre").css("color", "red");
			    			   $("#missatge-registre").text("Ja existeix l'usuari!");
			    			   $("#missatge-registre").css("font-size", "13px");
				    	   }
				    	   else if (data.indexOf("3") == 0) {
				    		   console.log("data.indexOf(3) == 0");
				    		   $("#missatge-registre").css("color", "red");
				    		   $("#missatge-registre").text("No existeix el client!");
				    		   $("#missatge-registre").css("font-size", "13px");
				    	   }
			    	   }
			    	   },
			    	   error: function(jqXHR, textStatus, errorThrown) {
			    		   console.log("UsuariServiceController error");
			    		   }
			    	   });
		} catch (e) { throw new Error(e.message); }
		console.log("return false");
		return false;
	};
	</script>
    <script>
    var madrid = ol.proj.fromLonLat([2.1533203124999, 41.41845703125]);

    var view = new ol.View({
      // the view's initial state
      center: madrid,
      zoom: 6
    });

    var map = new ol.Map({
      layers: [
        new ol.layer.Tile({
          preload: 4,
          source: new ol.source.OSM()
        })
      ],
      // Improve user experience by loading tiles while animating. Will make
      // animations stutter on mobile or slow devices.
      loadTilesWhileAnimating: true,
      target: 'map',
      controls: ol.control.defaults({
        attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
          collapsible: false
        })
      }),
      view: view
    });
    $(document).ready(function(){
    	try {
			console.log("addIdioma");
			$.ajax({
				url: 'http://localhost/compilador-cartografic/IdiomaServiceController?info=add&idioma=encatala',
			       type: 'POST',
			       success: function(data, textStatus, jqXHR) {
			    	   console.log("IdiomaServiceController success");
			    	   },
			    	   error: function(jqXHR, textStatus, errorThrown) {
			    		   console.log("IdiomaServiceController error");
			    		   }
			    	   });
		} catch (e) { throw new Error(e.message); }
    	/*for (var i = 1; i <= nombreDeFiles; i++) {
        	$('#addr'+i).html("<td>"+ i +"</td><td class='bk-td' id='file-name"+i+"'>" + "" + "</td><td class='bk-td' id='file-hour"+i+"'>" + "" + "</td><td class='bk-td'><input  id='checkbox-file"+i+"' type='checkbox' class='form-control ui-checkbox-file'></td><td class='bk-td' id='file-filter"+i+"'>" + "" + "</td>");
            $('#tab_logic').append('<tr id="addr' + (i + 1) + '"></tr>');
    	}*/
  	});
    $("#option-method").hide();
    </script>
  </body>
</html>