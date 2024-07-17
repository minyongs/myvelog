function likePost(postId) {
    fetch('/postapi/like/' + postId, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('좋아요 처리 중 오류가 발생했습니다.');
            }
        })
        .then(data => {
            document.getElementById('like-count').innerText = data.likeCount;
        })
        .catch(error => {
            alert(error.message);
        });
}

function deletePost(event, postId) {
    event.preventDefault();
    if (confirm('정말 삭제하시겠습니까?')) {
        fetch('/postapi/delete/' + postId, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById('delete-success').style.display = 'block';
                    setTimeout(function() {
                        window.location.href = '/myvelog';
                    }, 2000);
                } else {
                    alert('삭제 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                alert('삭제 중 오류가 발생했습니다.');
            });
    }
}

function showReplyForm(commentId) {
    document.getElementById('reply-form-' + commentId).style.display = 'block';
}

function hideReplyForm(commentId) {
    document.getElementById('reply-form-' + commentId).style.display = 'none';
}

function toggleReplies(commentId) {
    const replies = document.getElementById('replies-' + commentId);
    const button = document.getElementById('toggle-replies-' + commentId);
    if (replies.style.display === 'none' || replies.style.display === '') {
        replies.style.display = 'block';
        button.innerText = replies.childElementCount - 2 + '개의 답글';
    } else {
        replies.style.display = 'none';
        button.innerText = replies.childElementCount - 2 + '개의 답글';
    }
}
