<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
</head>
<style type="text/css">
	form {
		line-height: 50px;
	}
	button {
		height: 30px;
	}
	td {
		text-align: center;
	}
	body {
		width: 1200px;
		margin: auto;
	}
	table {
		width: 600px;
	}
</style>
<body>
	<header>
		<form action="form.html">
			<button type="submit">Cadastrar</button>
		</form>
		<form action="altera.php" method="POST">
			<button type="submit">Atualizar</button>
			<label for="id">Id do Filme</label>
			<input type="text" name="id">
			<label for="id">Novo Título</label>
			<input type="text" name="titulo">	
		</form>
		<form action="consulta.php" method="GET">
			<button type="submit">Consultar</button>
			<label for="texto">Pesquisa</label>
			<input type="text" name="texto">

			<select name="criterio">
				<option value="1">Título</option>
				<option value="2">Gênero</option>
				<option value="3">Diretor</option>
				<option value="4">Ano de Lançamento</option>
			</select>
		</form>
		<form action="remove.php" method="GET">
			<button type="submit">Remover</button>
			<label for="idr">Id do Filme</label>
			<input type="text " name="idr">
		</form>
	</header>
	<article>
		<h3>Filmes Cadastrados</h3>
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
					$cliente = new SoapClient('http://localhost:8080/app/gerencia?wsdl');
					$q = $cliente->__soapCall('get', array(array('texto' => 'aaa', 'criterio' => 5)));
					
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
	</article>


</body>
</html>