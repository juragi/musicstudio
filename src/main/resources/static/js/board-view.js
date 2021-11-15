console.log(boardId);

var vm = new Vue({
    el: "#app",
    data: {
        username: null,
        title: null,
        content: null,
        videoId: null,
        randomPlay: true,
        commentText: "",
        comments: []
    },
    mounted: function() {
        this.loadView(boardId);
    },
    methods: {
        loadView(boardId) {
            this.getBoardView(boardId).then(board=>{
                this.username = board.username;
                this.title = board.title;
                this.content = board.content;
                this.videoId = board.videoId;
                this.setupYoutubePlayer();
            });
            this.loadComments(boardId);
        },
        getBoardView: function(boardId) {
            var url = "/api/boards/" + boardId;
            console.log(this)
            return fetch(url).then(res=>res.json());
        },
        setupYoutubePlayer: function() {
            var tag = document.createElement('script');
            tag.src = "https://www.youtube.com/iframe_api";
            var firstScriptTag = document.getElementsByTagName('script')[0];
            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
        },
        randomNext: function() {
            var url = "/api/board/random";
            fetch(url).then(res=> {
                res.json().then(json => {
                    if(res.ok) {
                        location.href = "/board/view/" + json;
                    } else {
                        alert(json.message)
                    }
                    console.log(json);
                });
            })
        },
        loadComments: function() {
            //
            var url = "/api/board/comments";
            url += "?boardId=" + boardId;
            fetch(url).then(res=>res.json().then(json=> {
                if(res.ok) {
                    //
                    this.comments = json;
                } else {
                    alert(res.message)
                }
            }))
        },
        leaveComment: function() {
            if(this.commentText === "") {
                alert("Comment is empty.");
                return;
            }
            var url = "/api/boards/" + boardId + "/comment";
            var data = {
                content: this.commentText,
                board_id: boardId
            };
            fetch(url, {
                method: "POST",
                body: JSON.stringify(data),
                headers: {"Content-Type": "application/json"}
            })
            .then(res=>{
                res.json().then(json => {
                    if (res.ok) {
                        //
                        console.log(json);
                        this.commentText = "";
                        this.loadComments();
                    } else {
                        alert(json.message)
                    }
                })
            })
        },
        formattedDate: (date, format) => {
            return moment(date).format(format);
        }
    }
});



var player;
function onYouTubeIframeAPIReady() {
    console.log(new Date(), "haha");
    player = new YT.Player('player', {
        height: "320",
        width: "640",
        videoId: vm.videoId,
        //videoId: "58rk4DiNqzY",
        events: {
            "onReady": onPlayerReady,
            "onStateChange": onPlayerStateChange
        }
    });
}

function onPlayerReady(event) {
    player.playVideo();
}

function onPlayerStateChange(event) {
    if(event.data === 0) {
        if(vm.randomPlay) vm.randomNext();
        else player.playVideo();
    }
}

//fetch(url)
//    .then(res=>res.json())
//    .then(res=>{
//        console.log(res);
//    });