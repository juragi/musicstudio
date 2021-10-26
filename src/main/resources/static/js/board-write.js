var modal;
var vm = new Vue({
    el: "#app",
    data: {
        title: "",
        content: "",
        videoId: "",

        searchKeyword: "",
        searchResponse: {}
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
        
    }
});

document.getElementById("exampleModal").addEventListener('hidden.bs.modal', function() {
    vm.searchKeyword = "";
    vm.searchResponse = {};
});