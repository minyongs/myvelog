document.addEventListener("DOMContentLoaded", function() {
    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'header': [1, 2, false] }],
                ['bold', 'italic', 'underline'],
                [{ 'color': [] }, { 'background': [] }],
                ['link', 'image'],
                [{ 'list': 'ordered' }, { 'list': 'bullet' }]
            ]
        }
    });

    // Preview 기능 추가
    var titleInput = document.querySelector('.title-input');
    var previewTitle = document.getElementById('preview-title');
    var previewContent = document.getElementById('preview-content');
    var thumbnailInput = document.getElementById('thumbnail');
    var previewThumbnail = document.getElementById('preview-thumbnail');
    var visibilitySelect = document.getElementById('visibility');
    var previewVisibility = document.getElementById('preview-visibility');

    titleInput.addEventListener('input', function() {
        previewTitle.textContent = titleInput.value;
    });

    quill.on('text-change', function() {
        previewContent.innerHTML = quill.root.innerHTML;
    });

    thumbnailInput.addEventListener('change', function() {
        var file = thumbnailInput.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                previewThumbnail.innerHTML = '<img src="' + e.target.result + '" alt="썸네일" style="max-width: 100%;">';
            };
            reader.readAsDataURL(file);
        } else {
            previewThumbnail.innerHTML = '';
        }
    });

    visibilitySelect.addEventListener('change', function() {
        var visibility = visibilitySelect.value;
        previewVisibility.textContent = visibility === 'public' ? '공개' : '비공개';
    });

    document.getElementById('postForm').addEventListener('submit', function(event) {
        var contentTextArea = document.getElementById('content');
        contentTextArea.value = quill.root.innerHTML; // Quill 에디터의 HTML 내용을 숨겨진 필드에 설정
    });
});
