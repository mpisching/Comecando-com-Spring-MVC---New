$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoTitulo = button.data('codigo'); 
	var descricaoTitulo = button.data('descricao'); 
	
	var modal = $(this); 
	
	var form = modal.find('form'); 
	
	var action = form.data('url-base');
	if (!action.endsWith('/')) { 
		action += '/'; 
	} 
	form.attr('action', action + codigoTitulo);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>' + descricaoTitulo + '</strong>?'); //localiza o class modal-body e a tag span no DialogoConfirmacaoExclusao.html para mudar a mensagem para o usuário, isto é, aparecerá o texto do span mais a descrição de um título dinâmicamente conforme a seleção do usuário 
	
});

$(function() {
	$('[rel="tooltip"]').tooltip();//ativa o tooltip no botões editar e excluir
});