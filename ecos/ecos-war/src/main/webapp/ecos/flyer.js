'use strict';

//
// Fetch the PDF document from the URL using promises
//
PDFJS.getDocument('flyer.pdf').then(function(pdf) {
	alert(2);
	// Using promise to fetch the page
	pdf.getPage(1).then(function(page) {
    var scale = 1.5;
    var viewport = page.getViewport(scale);
    alert(2);
    //
    // Prepare canvas using PDF page dimensions
    //
    var canvas = document.getElementById('pdfCanvas');
    alert(canvas);
    var context = canvas.getContext('2d');
    canvas.height = viewport.height;
    canvas.width = viewport.width;
    alert(2);
    //
    // Render PDF page into canvas context
    //
    var renderContext = {
      canvasContext: context,
      viewport: viewport
    };
    page.render(renderContext);
  });
});
