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
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true}); //aqui está dizendo... "carregou a página, encontra todos (indicado pelo .) os elementos que tem a classe js-currency" e chama a função maskMoney com alguns parámetros (decimal, thousands, allowZero, e muito mais) para aplicar a máscara em todos os elementos desta classe
	
	$('.js-atualizar-status').on('click', function(event) {
		//console.log('clicou...');
		//alert('clicked');
		event.preventDefault(); //não faz o comportamento default do link
		
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		
		console.log('urlReceber', urlReceber); //para ver esse resultado é necessário abrir o console do inspecionar do navegador
		
		//função ajax
		//irá criar uma requisição PUT que será enviada automaticamente para o controller (ver o método receber() no controller) -- RECEBER aqui é o RECEBER o pagamento de um título
		var response = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		//se a requisição acima der sucesso executa done caso contrário executa fail
		response.done(function(e){
			var codigoTitulo = botaoReceber.data('codigo');
			$('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">' + e + '</span>');//essa linha vai substituir o span da linha 39 do PesquisaTitulos.html
			botaoReceber.hide();
			
		});
		
		response.fail(function(e){
			console.log(e);
			alert('Erro recebendo cobrança!');
		});
	});
});

