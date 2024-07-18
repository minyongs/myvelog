document.addEventListener("DOMContentLoaded", function() {
    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'font': [] }],
                [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                ['bold', 'italic', 'underline', 'strike'],
                [{ 'color': [] }, { 'background': [] }],
                [{ 'script': 'sub'}, { 'script': 'super' }],
                ['blockquote', 'code-block'],
                [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                [{ 'indent': '-1'}, { 'indent': '+1' }],
                [{ 'direction': 'rtl' }],
                [{ 'align': [] }],
                ['link', 'image', 'video'],
                ['clean']
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
                previewThumbnail.innerHTML = '<img src="' + e.target.result + '" alt="썸네일 이미지" class="img-thumbnail" style="max-width: 100%;">';
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

    // Form submission
    function submitForm(action) {
        var contentTextArea = document.getElementById('content');
        contentTextArea.value = quill.root.innerHTML;

        var formData = new FormData(document.getElementById('postForm'));
        formData.append('action', action);

        var postId = document.getElementById('postId').value;

        fetch(`/myvelog/edit/${postId}`, {
            method: 'POST', // POST로 변경
            body: formData
        }).then(response => {
            if (response.ok) {
                window.location.href = `/myvelog/detail/${postId}`;
            } else {
                alert('수정 실패');
            }
        }).catch(error => {
            console.error('Error:', error);
        });
    }

    // Bind submitForm to buttons
    document.querySelectorAll('.save-button').forEach(function(button) {
        button.addEventListener('click', function() {
            var action = this.value;
            submitForm(action);
        });
    });
});
