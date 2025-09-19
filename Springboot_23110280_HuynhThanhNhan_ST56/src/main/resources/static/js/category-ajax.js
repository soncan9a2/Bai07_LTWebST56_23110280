$(document).ready(function() {
  // Render category list into any table with data-role="category-table"
  $("table[data-role='category-table']").each(function() {
    const $table = $(this);
    $.getJSON('/api/category', function(resp) {
      const data = Array.isArray(resp) ? resp : (resp.body || []);
      const rows = [];
      data.forEach(function(item){
        const id = item.cateId || item.categoryId || item.id;
        const name = item.cateName || item.categoryName;
        const icon = item.icons || item.icon;
        rows.push(
          '<tr>'+
            '<td>'+ id +'</td>'+
            '<td>'+ (icon ? ('<img src="/uploads/'+icon+'" style="width:60px;height:60px;object-fit:cover">') : '') +'</td>'+
            '<td>'+ name +'</td>'+
            '<td>'+
              '<button class="btn btn-sm btn-warning" data-action="edit" data-id="'+id+'">Edit</button> '+
              '<button class="btn btn-sm btn-danger" data-action="delete" data-id="'+id+'">Delete</button>'+
            '</td>'+
          '</tr>'
        );
      });
      $table.find('tbody').html(rows.join(''));
    });
  });

  // Create
  $(document).on('submit', 'form#addCategory', function(e){
    e.preventDefault();
    const fd = new FormData(this);
    $.ajax({ url: '/api/category/addCategory', type: 'POST', data: fd, processData: false, contentType: false })
      .done(function(){ location.reload(); });
  });

  // Update
  $(document).on('submit', 'form#updateCategory', function(e){
    e.preventDefault();
    const fd = new FormData(this);
    $.ajax({ url: '/api/category/updateCategory', type: 'PUT', data: fd, processData: false, contentType: false })
      .done(function(){ location.reload(); });
  });

  // Delete
  $(document).on('click', 'button[data-action="delete"][data-id]', function(){
    const id = $(this).data('id');
    if(confirm('Delete this category?')){
      $.ajax({ type: 'DELETE', url: '/api/category/deleteCategory?categoryId='+id })
        .done(function(){ location.reload(true); });
    }
  });
});


