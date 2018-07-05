<?php
	$cliente = new SoapClient('http://localhost:8080/app/gerencia?wsdl', array('trace' => 1));
	ini_set('default_socket_timeout', 600);

	$cliente->__soapCall('update', array(array('id' => $_POST['id'], 'titulo' => $_POST['titulo'])));

	require_once 'index.php';
?>