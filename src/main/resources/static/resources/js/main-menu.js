$(function() {
	$('#quote-link').on(
			'click',
			function() {
				$(this).attr(
						'alt',
						$('#save-unquoted-url').val()
								+ $('#last-displayed-quote').val());
			});
	$('.action-link').on('click', function() {
		$('#content-displayed').load($(this).attr('alt'));
		$('.nav > .active').removeClass('active');
		$(this).parent().addClass('active');
	});
});
