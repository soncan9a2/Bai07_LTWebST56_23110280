$(document).ready(function() {
  // Render product list into any table with data-role="product-table"
  $("table[data-role='product-table']").each(function() {
    const $table = $(this);
    $.getJSON('/api/product', function(resp) {
      const data = Array.isArray(resp) ? resp : (resp.body || []);
      const rows = [];
      data.forEach(function(p){
        const id = p.productId || p.id;
        rows.push(
          '<tr>'+
            '<td>'+ id +'</td>'+
            '<td>'+ (p.images ? ('<img src="/uploads/'+p.images+'" style="width:60px;height:60px;object-fit:cover">') : '') +'</td>'+
            '<td>'+ p.productName +'</td>'+
            '<td>'+ p.unitPrice +'</td>'+
            '<td>'+ p.quantity +'</td>'+
            '<td>'+
              '<button class="btn btn-sm btn-warning" data-action="edit-product" data-id="'+id+'">Edit</button> '+
              '<button class="btn btn-sm btn-danger" data-action="delete-product" data-id="'+id+'">Delete</button>'+
            '</td>'+
          '</tr>'
        );
      });
      $table.find('tbody').html(rows.join(''));
    });
  });

  // Create
  $(document).on('submit', 'form#addProduct', function(e){
    e.preventDefault();
    const fd = new FormData(this);
    const hasId = (fd.get('productId') || '').toString().length > 0;
    const url = hasId ? '/api/product/updateProduct' : '/api/product/addProduct';
    const method = hasId ? 'PUT' : 'POST';
    $.ajax({ url: url, type: method, data: fd, processData: false, contentType: false })
      .done(function(){ location.reload(); })
      .fail(function(xhr){
        const msg = xhr && xhr.responseText ? xhr.responseText : 'Submit failed';
        alert(msg);
        console.error('Product submit error:', xhr);
      });
  });

  // Update
  $(document).on('submit', 'form#updateProduct', function(e){
    e.preventDefault();
    const fd = new FormData(this);
    $.ajax({ url: '/api/product/updateProduct', type: 'PUT', data: fd, processData: false, contentType: false })
      .done(function(){ location.reload(); });
  });

  // Delete
  $(document).on('click', 'button[data-action="delete-product"][data-id]', function(){
    const id = $(this).data('id');
    if(confirm('Delete this product?')){
      $.ajax({ type: 'DELETE', url: '/api/product/deleteProduct?productId='+id })
        .done(function(){ location.reload(true); });
    }
  });

  // Edit fill form
  $(document).on('click', 'button[data-action="edit-product"][data-id]', function(){
    const id = $(this).data('id');
    $.getJSON('/api/product/get?id='+id, function(resp){
      const p = resp.body || resp;
      const $f = $('form#addProduct');
      $f.find('input[name="productId"]').val(p.productId);
      $f.find('input[name="productName"]').val(p.productName);
      $f.find('input[name="unitPrice"]').val(p.unitPrice);
      $f.find('input[name="discount"]').val(p.discount);
      $f.find('input[name="quantity"]').val(p.quantity);
      $f.find('input[name="status"]').val(p.status);
      $f.find('input[name="categoryId"]').val(p.category ? p.category.cateId : '');
      $f.find('input[name="description"]').val(p.description);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  });

  // Reset form
  $(document).on('click', '#resetProductForm', function(){
    const $f = $('form#addProduct');
    $f[0].reset();
    $f.find('input[name="productId"]').val('');
  });
});


