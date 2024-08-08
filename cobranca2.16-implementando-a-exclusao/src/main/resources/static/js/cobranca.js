$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	//pegando o botão que desparou o evento
	var button = $(event.relatedTarget);
	
	var codigoTitulo = button.data('codigo'); //recupera os atributos do tipo data do form, no caso o codigo do titulo
	var descricaoTitulo = button.data('descricao'); //função data do jQuery para obter o valor de um atributo, que foi definido pelo th:attr
	
	var modal = $(this); //pegando o objet jquery
	
	var form = modal.find('form'); //método para encontrar o componente form
	
	//var action = form.attr('action'); //dentro do form pega o atributo action, isso é, o action do form, isto é, "<form action="/titulos" method="POST">" do DialogoConfirmacaoExclusao.html
	//a linha anterior agora vai ser substituída para recuperar a url-base de data 
	var action = form.data('url-base');
	if (!action.endsWith('/')) { //truque para concatenar uma barra, se for necesário, ao action do form
		action += '/'; //para adicionar '/' após o nome da action do form = "/titulos/..."
	} 
	form.attr('action', action + codigoTitulo);//altera o action original do form
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>' + descricaoTitulo + '</strong>?'); //localiza o class modal-body e a tag span no DialogoConfirmacaoExclusao.html para mudar a mensagem para o usuário, isto é, aparecerá o texto do span mais a descrição de um título dinâmicamente conforme a seleção do usuário 
	
	//alert(codigoTitulo);
});