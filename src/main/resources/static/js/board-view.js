console.log(boardId);

var vm = new Vue({
    el: "#app",
    data: {
        username: null,
        title: null,
        content: null,
        videoId: null
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
                this.videoId = "58rk4DiNqzY";
                this.setupYoutubePlayer();
            });
        },
        getBoardView: function(boardId) {
            var url = "/api/boards/" + boardId;
            console.log(this)
            return fetch(url).then(res=>res.json());
        },
        setupYoutubePlayer: function() {
            
        }
    }
});

var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

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
    console.log(event);
}

//fetch(url)
//    .then(res=>res.json())
//    .then(res=>{
//        console.log(res);
//    });