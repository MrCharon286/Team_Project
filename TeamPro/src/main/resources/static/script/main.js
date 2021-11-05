$(function() {
	$("#page").append('<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>');
	const $head = $("head")[0];
	$('<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>').appendTo($head)
	$('<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>').appendTo($head);
	$('<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>').appendTo($head);
	$('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">').appendTo($head);
	$('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css">').appendTo($head);
	$('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">').appendTo($head);

});
	
function getCsrfTokenHeader(xhr) {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	xhr.setRequestHeader(header, token);
	return xhr;
}