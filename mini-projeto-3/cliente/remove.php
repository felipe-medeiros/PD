<?php
	$cliente = new SoapClient('http://localhost:8080/app/gerencia?wsdl', array('trace' => 1));
	ini_set('default_socket_timeout', 600);

	
	$cliente->__soapCall('remove', array(array('id' => $_GET['idr'])));
	
	require_once 'index.php';
?>