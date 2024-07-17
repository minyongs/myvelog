var quill = new Quill('#editor', {
    theme: 'snow',
    modules: {
        toolbar: [
            [{ 'font': [] }],
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
            ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
            [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
            [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
            ['blockquote', 'code-block'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
            [{ 'direction': 'rtl' }],                         // text direction
            [{ 'align': [] }],
            ['link', 'image', 'video'],
            ['clean']                                         // remove formatting button
        ]
    }
});

// Preview 기능 추가
var titleInput = document.querySelector('.title-input');
var previewTitle = document.getElementById('preview-title');
var previewContent = document.getElementById('preview-content');

titleInput.addEventListener('input', function() {
    previewTitle.textContent = titleInput.value;
});

quill.on('text-change', function() {
    previewContent.innerHTML = quill.root.innerHTML;
});

function submitForm(action) {
    var contentTextArea = document.getElementById('content');
    contentTextArea.value = quill.root.innerHTML;

    var formData = new FormData(document.getElementById('postForm'));
    formData.append('action', action);

    var postId = document.getElementById('postId').value;

    fetch(`/myvelog/edit/${postId}`, {
        method: 'PUT',
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
