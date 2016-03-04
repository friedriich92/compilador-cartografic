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
	<!-- rel -->
	<link rel="shortcut icon" href="./img/logo.ico">
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/estils.css">
	<link rel="stylesheet" href="./css/jumbotron-narrow.css">
	<link rel="stylesheet" href="./css/ol.css">
</head>
<body>
    <div class="container">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active" id="registre" onclick="myClickFunction()"><a id="registre-text" href="#">Registrar-se</a></li>
            <li role="presentation"><a href="#" id="entrar" onclick="myClickFunction2()">Entrar</a></li>
            <li class="dropdown" id="idioma">
			  <button class="btn btn-default dropdown-toggle" onclick="myClickFunction3()" type="button" id="idioma-menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    Idioma
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="idioma-menu">
			    <li><a href="#" class="ui-idioma-boto" onclick="myClickFunction11()" id="idioma-catala">CA</a></li>
			    <li role="separator" class="divider"></li>
			    <li><a href="#" class="ui-idioma-boto" onclick="myClickFunction12()" id="idioma-castellano">ES</a></li>
			  </ul>
			</li>
          </ul>
        </nav>
        <h3 class="text-muted" id="titol">Compilador Cartogr&agrave;fic</h3>
      </div>
      <div class="text-center ui-entrar-formulari" id="entrar-formulari">
      	<div class="logo ui-entrar-text" id="entrar-text">Entrar
      	<button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="myClickFunction13()"><span aria-hidden="true">&times;</span></button>
      	</div>
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
      	<button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="myClickFunction14()"><span aria-hidden="true">&times;</span></button>
      	</div>
      		<div class="login-form-1">
      			<form id="register-form" class="text-left">
      				<div class="login-form-main-message"></div>
						<div class="main-login-form">
							<div class="login-group">
						<div class="form-group">
							<input type="text" class="form-control" id="rg_username" name="lg_username" placeholder="Username">
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
					</div>
					<button type="submit" class="login-button ui-registre-formulari-boto">Sign In<i class="fa fa-chevron-right"></i></button>
						</div>
				</form>
			</div>
		</div>
      <div class="jumbotron">
      	<h2 class="ui-opcio-a" id="opcio-a">M&egrave;tode Principal</h2>
<!--         <h3 class="ui-importacio" id="importacio">ImportaciÃ³</h3> -->
        <p class="lead ui-importacio-text" id="importacio-text">Selecciona un fitxer (formats: SHP, OSM, XML o CSV)</p>
        <form method="POST" enctype="multipart/form-data">
			<input type="file" name="fileUploader" id="fileUploader" class="ui-boto-pujar-fitxer"/>
	  	</form>
	  	<p class="ui-puja-fitxer" id="puja-fitxer"></p>
	  	<div class="container ui-container-table" id="container-table">
   		 <div class="row clearfix">
			<div class="col-md-12 column">
				<table class="table table-bordered table-hover" id="tab_logic">
					<thead>
						<tr >
							<th class="text-center">#</th>
							<th class="text-center" id="taula-atribut2">Fitxer</th>
							<th class="text-center">Hora</th>
							<th class="text-center" id="taula-atribut4">Selecci&oacute;</th>
							<th class="text-center" id="taula-atribut5">Filtres (#Files)</th>
						</tr>
					</thead>
					<tbody>
						<tr id='addr0'>
						</tr>
	                    <tr id='addr1'></tr>
					</tbody>
				</table>
			</div>
		</div>
	  	</div>
	  	<button type="button" class="btn btn-info btn-lg ui-aplicar-canvi-boto" data-toggle="modal" data-target="#myModal">Aplicar Canvi</button>
	  	<a class="btn btn-lg btn-success ui-exportar-boto" id="exportar-boto1" role="button" onclick="myClickFunction6()">Exportar</a>
      	<p class="ui-exporta-fitxer" id="exporta-fitxer"></p>
      </div>
      <!-- Modal -->
      <div class="modal fade" id="myModal" role="dialog">
       <div class="modal-dialog"> 
      <!-- Modal content-->
        <div class="modal-content">
         <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Aplicar Canvi</h4>
         </div>
         <div class="modal-body">
          <p>Tria el canvi a realitzar en el fitxer:</p>
            <form role="form">
			    <div class="radio">
			      <label><input type="radio" onClick="myClickFunction17()" id="radioCoordenades" name="optradio">Coordenades</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentCoordenades" placeholder="EPSG:4326" disabled></textarea>
			    </div>
			    <div class="radio">
			      <label><input type="radio" onClick="myClickFunction18()" id="radioEstil" name="optradio">Estil</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentEstil" placeholder="street=blue" disabled></textarea>
			    </div>
			    <div class="radio">
			      <label><input type="radio" onClick="myClickFunction19()" id="radioFiltre" name="optradio">Filtre</label>
			      <textarea class="form-control ui-textarea" rows="1" id="commentFiltre" placeholder="rows=20, tables=tablename, rows=20;tables=tablename" disabled></textarea>
			    </div>
		  	</form>
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="myClickFunction20()" data-dismiss="modal">Aplicar</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>  
    </div>
  	</div>
      <div class="jumbotron">
        <h2 class="ui-opcio-a" id="opcio-b">M&egrave;tode Opcional</h2>
<!--         <h3 class="ui-modificacio" id="modificacio">ImportaciÃ³</h3> -->
        <p class="lead ui-importacio-text" id="importacio-text2">Selecciona una porci&oacute; del mapa:</p>
		<div class="row-fluid">
	  		<div class="span12">
	    	<div id="map" class="ui-map"></div>
	  		</div>
		</div>
<!-- 		<div class="row-fluid"> -->
<!--         <p class="lead" id="modificacio-text">Triar format de sortida:</p> -->
<!--         <table class="table"> -->
<!-- 		    <tbody> -->
<!-- 		        <tr> -->
<!-- 		            <td id="formats">Format</td> -->
<!-- 		            <td id="formats-opcio">--</td> -->
<!-- 		            <td id="formats-boto" class="ui-seguent-boto" role="button" onclick="myClickFunction9()"></td> -->
<!-- 		        </tr> -->
<!-- 		    </tbody> -->
<!-- 		</table> -->
<!--         <p><a class="btn btn-lg btn-success" id="modificacio-boto" href="#" role="button" onclick="myClickFunction5()">Aplicar canvi</a></p> -->
<!--       </div> -->
    	<a class="btn btn-lg btn-success ui-exportar-boto" id="exportar-boto" role="button" onclick="myClickFunction6b()">Exportar</a>
    	<div id="myProgress" class="ui-myProgress"><div id="myBar" class="ui-myBar"></div></div>
    	<p class="ui-exporta-fitxer-mapa" id="exporta-fitxer-mapa"></p>
    </div>
    <footer class="footer">
    	<p>&copy; 2015 SITEP, Inc.</p>
    </footer>
    <!-- /container -->
    <script>
	function myClickFunction() {
		console.log("registre");
		$("#registre-formulari").show();
		$("#entrar-formulari").hide();
	}; 
	function myClickFunction2() {
		++entryVariable;
		console.log("entrar (myClickFunction2): " +entryVariable);
		if ($("#entrar").text().indexOf("Log out") == 0) {
			$("#entrar").text("Entrar");
			$("#lg_username").text("");
			$("#lg_password").text("");
			entryVariable = 0;
		}
		else {
			$("#entrar-formulari").show();
			$("#registre-formulari").hide();
		}
	}; 
	function myClickFunction3() {
		console.log("idioma");
	}; 
	function myClickFunction4() {
		console.log("importacio-boto");
		window.open("http://localhost/compilador-cartografic/CapaOfflineServiceServlet", "CapaOfflineServiceServlet");
	};
	function myClickFunction6() {
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
			var fileNameStringEmpty = "";
			console.log(fileNameString.valueOf() + " ==? " + fileNameStringEmpty.valueOf());
			console.log(fileNameString.valueOf() == fileNameStringEmpty.valueOf());
			if (fileNameString.valueOf() == fileNameStringEmpty.valueOf()) {
				$("#exporta-fitxer").text("S'ha seleccionat una columna buida!");
	        	$("#exporta-fitxer").css("color", "red");
	        	$("#exporta-fitxer").css("font-size", "13px");
			}
			else {
				$.get("http://localhost/compilador-cartografic/CapaOfflineServiceServlet?name="+fileNameString);
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
			url: 'http://localhost/compilador-cartografic/FonsOfflineServiceServlet',
	        type: 'POST',
	        data: boundingBoxString,
	        success: function(data, textStatus, jqXHR) {
	        	$("#myProgress").hide();
	        	window.open("http://localhost/compilador-cartografic/FonsOfflineServiceServlet", "FonsOfflineServiceServlet");
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
	function myClickFunction11() {
		idiomaVariable = 0;
		console.log("idioma-catala");
		$("#titol").text("Compilador Cartografic");
		$("#opcio-a").text("Metode A");
		$("#opcio-b").text("Metode B");
		$("#importacio-text2").text("Selecciona una porcio del mapa:");
		$("#taula-atribut2").text("Fitxer");
		$("#taula-atribut4").text("Seleccio");
		$("#registre-text").text("Registrar-se");
		$("#importacio").text("Importacio");
		$("#importacio-text").text("Selecciona un fitxer (formats: SHP, OSM, XML o CSV)");
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
	function myClickFunction12() {
		idiomaVariable = 1;
		console.log("idioma-castella");
		$("#titol").text("Compilador Cartografico");
		$("#opcio-a").text("Metodo A");
		$("#opcio-b").text("Metodo B");
		$("#importacio-text2").text("Selecciona una porcion del mapa:");
		$("#taula-atribut2").text("Fichero");
		$("#taula-atribut4").text("Seleccion");
		$("#registre-text").text("Registrarse");
		$("#importacio").text("Importacion");
		$("#importacio-text").text("Selecciona un fichero (formatos: SHP, OSM, XML o CSV)");
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
	function myClickFunction13() {
		console.log("entrar-close");
		$("#entrar-formulari").hide();
	};
	function myClickFunction14() {
		console.log("registre-close");
		$("#registre-formulari").hide();
	};
	function myClickFunction15() {
		$("#entrar-formulari").hide();
	};
	function myClickFunction17() {
		$("#commentCoordenades").attr("disabled", false); 
		$("#commentEstil").attr("disabled", true);
		$("#commentFiltre").attr("disabled", true);
	};
	function myClickFunction18() {
		$("#commentCoordenades").attr("disabled", true); 
		$("#commentEstil").attr("disabled", false);
		$("#commentFiltre").attr("disabled", true);
	};
	function myClickFunction19() {
		$("#commentCoordenades").attr("disabled", true); 
		$("#commentEstil").attr("disabled", true);
		$("#commentFiltre").attr("disabled", false);
	};
	function myClickFunction20() {
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
						url: 'http://localhost/compilador-cartografic/AplicarCanviServiceServlet?name='+fileNameString+'&user='+userSession+'&info='+optionText+'&c='+optionChange,
						type: 'POST',
// 						data: '',
						success: function(data, textStatus, jqXHR) {
							console.log("UsuariServiceServlet success " + data);
							if (optionChange.indexOf("coord") == 0) {
								if (data.indexOf("SI") == 0) {
									$("#exporta-fitxer").text("El canvi s'ha realitzat correctament.");
						        	$("#exporta-fitxer").css("color", "green");
						        	$("#exporta-fitxer").css("font-size", "13px");
								}
								else {
									$("#user-login-text").css("color", "red");
									if (optionChange.indexOf("coord") == 0)
										$("#user-login-text").text("Les coordenades introduides no permeten realitzar el canvi.");
									else if (optionChange.indexOf("estil") == 0)
										$("#user-login-text").text("L'estil introduit no permet realitzar el canvi.");
									else if (optionChange.indexOf("filtre") == 0)
										$("#user-login-text").text("El filtre introduit no permet realitzar el canvi.");
									$("#user-login-text").css("font-size", "13px");								
								}
							}
							else if (optionChange.indexOf("filtre") == 0) {
								$("#file-filter" + (numberOfFile)).text(data);
								}
							else {
								/* Not implemented yet */
							}
							},
							error: function(jqXHR, textStatus, errorThrown) {
								console.log("AplicarCanviServiceServle error" + data);
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
	function postFilesData(data)
	{
		console.log("postFilesData");
		$.ajax({
			url: 'http://localhost/compilador-cartografic/ImportarFitxerServiceServlet?name='+userSession, //+'&filename='+file2uploadName
	        type: 'POST',
	        data: data,
	        cache: false,
	        dataType: 'json',
	        processData: false, 
	        contentType: false, 
	        success: function(data, textStatus, jqXHR) {
	        	DateTime();
	        	$("#puja-fitxer").css("color", "green");
	        	$("#puja-fitxer").text("El fitxer " + file2uploadName + " s'ha pujat correctament.");
	        	$("#puja-fitxer").css("font-size", "13px");
	        	if (filesUploaded >= 1 && filesUploaded <= nombreDeFiles) {
	        		$("#file-name" + filesUploaded).text(file2uploadName);
	        		$("#file-hour" + today).text(today);
	        	}
	        	else {
		        	$('#addr'+filesUploaded).html("<td>"+ (filesUploaded+1) +"</td><td>" + file2uploadName + "</td><td>" + today + "</td><td><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td id='file-filter"+filesUploaded+"'>" + "" + "</td>");
		            $('#tab_logic').append('<tr id="addr' + (filesUploaded + 1) + '"></tr>');
	        	}
	        	filesUploaded++;
	        	uploadToDatabase(file2uploadName);
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	        	console.log('ERRORS: ' + textStatus);
	        	DateTime();
	        	$("#puja-fitxer").css("color", "green"); // red
	        	$("#puja-fitxer").text("El fitxer " + file2uploadName + " s'ha pujat correctament.");
	        	$("#puja-fitxer").css("font-size", "13px");
	        	if (filesUploaded >= 1 && filesUploaded <= nombreDeFiles) {
		        	$("#file-name" + filesUploaded).text(file2uploadName);
	        		$("#file-hour" + filesUploaded).text(today);
	        	}
	        	else {
		        	$('#addr'+filesUploaded).html("<td>"+ (filesUploaded) +"</td><td>" + file2uploadName + "</td><td>" + today + "</td><td><input  id='checkbox-file"+filesUploaded+"' type='checkbox' class='form-control ui-checkbox-file'></td><td id='file-filter"+filesUploaded+"'>" + "" + "</td>");
		            $('#tab_logic').append('<tr id="addr' + (filesUploaded) + '"></tr>');
	        	}
	            filesUploaded++;
	            uploadToDatabase(file2uploadName);
	        }
	     });
	};
	function uploadToDatabase(filename) {
		$.ajax({
			url: 'http://localhost/compilador-cartografic/CarregadorDeFitxersVectorialsServiceServlet',
	        type: 'POST',
	        data: filename,
	        success: function(data, textStatus, jqXHR) {
				console.log("uploadToDatabase success");
				$("#file-filter" + (filesUploaded-1)).text(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log("uploadToDatabase error");
					}
				});
	};
	$('#fileUploader').on('change', uploadFile);
	$("#entrar-formulari").hide();
	$("#registre-formulari").hide();
	$("#myProgress").hide();
	function enterValidation(evt) {
		try {
			userSession = $("#lg_username").val();
			console.log("Nom: " + $("#lg_username").val() + ", Pass: " + $("#lg_password").val());
			var userInformation = "1," + $("#lg_username").val() + "," + $("#lg_password").val();
			console.log("userInformation: " + userInformation);
			$.ajax({
				url: 'http://localhost/compilador-cartografic/UsuariServiceServlet',
				type: 'POST',
				data: userInformation,
				success: function(data, textStatus, jqXHR) {
					console.log("UsuariServiceServlet success " + data);
					if (data.indexOf("SI") == 0) {
						$("#user-login-text").text("");
						myClickFunction15();
						++entryVariable;
						console.log("entrar (myClickFunction15): " +entryVariable);
						$("#entrar").text("Log out");
						return true;
						}
					else {
						$("#user-login-text").css("color", "red");
						$("#user-login-text").text("Les dades introduides no son correctes.");
						$("#user-login-text").css("font-size", "13px");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log("UsuariServiceServlet error" + data);
						}
					});
			}
		catch (e) { throw new Error(e.message); }
		return false;
	};
	$("#register-form").submit(function(e) {
		console.log("Nom: " + $("#rg_username").val() + ", Pass: " + $("#rg_password").val() + ", Email: " +
			$("#rg_email").val() + ", Rol: " + $("#rg_rol").val());
		var userInformation = "0," + $("#rg_username").val() + "," + $("#rg_password").val() + "," + 
			$("#rg_email").val() + "," + $("#rg_rol").val();
		console.log("userInformation: " + userInformation);
		$.ajax({
			url: 'http://localhost/compilador-cartografic/UsuariServiceServlet',
		       type: 'POST',
		       data: userInformation,
		       success: function(data, textStatus, jqXHR) {
		    	   console.log("UsuariServiceServlet success");
		    	   },
		    	   error: function(jqXHR, textStatus, errorThrown) {
		    		   console.log("UsuariServiceServlet error");
		    		   }
		    	   });
	});
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
    	for (var i = 1; i <= nombreDeFiles; i++) {
        	$('#addr'+i).html("<td>"+ i +"</td><td id='file-name"+i+"'>" + "" + "</td><td id='file-hour"+i+"'>" + "" + "</td><td><input  id='checkbox-file"+i+"' type='checkbox' class='form-control ui-checkbox-file'></td><td id='file-filter"+i+"'>" + "" + "</td>");
            $('#tab_logic').append('<tr id="addr' + (i + 1) + '"></tr>');
    	}
  	});
    </script>
  </body>
</html>