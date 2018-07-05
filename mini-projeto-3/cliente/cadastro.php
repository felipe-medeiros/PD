<?php
	$cliente = new SoapClient('http://localhost:8080/app/gerencia?wsdl', array('trace' => 1));
	ini_set('default_socket_timeout', 600);

	$cliente->__soapCall('add', array(array('titulo' => $_POST['titulo'],
									   'diretor' => $_POST['diretor'],
									   'estudio' => $_POST['estudio'],
									   'genero' => $_POST['genero'],
								       'ano' => $_POST['ano'])));
	
	require_once 'index.php';
?>