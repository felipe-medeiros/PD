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
		<form action="http://localhost:8080/app/webresources/filmes/atualiza" method="POST">
			<button type="submit">Atualizar</button>
			<label for="id">Id do Filme</label>
			<input type="text" name="id">
			<label for="id">Novo Título</label>
			<input type="text" name="titulo">	
		</form>
		<form action="http://localhost:8080/app/webresources/filmes/consulta" method="GET">
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
		<form action="http://localhost:8080/app/webresources/filmes/remove" method="POST">
			<button type="submit">Remover</button>
			<label for="idr">Id do Filme</label>
			<input type="text" name="idr">
		</form>
	</header>
	


</body>
</html>