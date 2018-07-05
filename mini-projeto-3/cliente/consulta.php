<?php
	$cliente = new SoapClient('http://localhost:8080/app/gerencia?wsdl');
	ini_set('default_socket_timeout', 600);

	$q = $cliente->__soapCall('get', array(array('texto' => $_GET['texto'], 'criterio' => $_GET['criterio'])));

	
?>
<!DOCTYPE html>
<html>
<head>
	<title>Consulta</title>
</head>
<body>
	<form action="index.php">
		<button>Voltar</button>
	</form>
	<table>
			<thead>
				<td>ID</td>
				<td>Ano de Lançamento</td>
				<td>Diretor</td>
				<td>Estúdio</td>
				<td>Gênero</td>				
				<td>Título</td>
			</thead>
			<tbody>
				<?php 
					
					if(isset($q->return) && is_array($q->return)){
						$lista = $q->return;
						foreach ($lista as $key => $value) {
							echo "<tr>";
							foreach ($value->item as $key => $value) {
								echo "<td>".$value."</td>";
							}
							echo "<tr>";
						}
					}elseif (isset($q->return)) {
						print_r($q->return);
					}						
				?>
			</tbody>
		</table>
</body>
</html>