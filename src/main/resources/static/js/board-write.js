var modal;
var vm = new Vue({
    el: "#app",
    data: {
        title: "",
        content: "",
        videoId: "",

        searchKeyword: "",
        searchResponse: {},
        submitting: false
    },
    mounted: function() {
        console.log(this);
        modal = new bootstrap.Modal(document.getElementById("exampleModal"));
    },
    methods: {
        showYoutubeModal: function(e) {
            //
            e.preventDefault();
            console.log(this, e);
            modal.show();
        },
        searchYoutube: function() {
            console.log(this.searchKeyword);
            var url = "/api/search-video";
            fetch(url, {
                method: "POST",
                body: JSON.stringify({searchKeyword: this.searchKeyword, pageToken: null}),
                headers: {
                    "Content-Type": "application/json"
                }
            })
            .then(res => res.json())
            .then(res => {
                console.log(res);
                this.searchResponse = res;
            })
        },
        selectVideo: function(videoId, title) {
            console.log(videoId);
            this.videoId = videoId;
            if(this.title === "") this.title = title;
            modal.hide();
        },
        submit: function() {
            if(this.submitting) return;
            this.submitting = true;
            var url = "/api/board/write";
            fetch(url, {
                method: "POST",
                body: JSON.stringify({title: this.title, videoId: this.videoId, content: this.content}),
                headers: {
                    "Content-Type": "application/json"
                }
            })
            .then(res => {
                this.submitting = false;
                console.log(res);
                //return res.json()
                res.json().then(json => {
                    if(res.ok) {
                        location.href = "/board/list";
                    } else {
                        alert(json.message)
                    }
                    console.log(json);
                });
            })
        }
    }
});

document.getElementById("exampleModal").addEventListener('hidden.bs.modal', function() {
    vm.searchKeyword = "";
    vm.searchResponse = {};
});