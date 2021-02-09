function toggleMainTab(tab) {
    var tabs = document.querySelectorAll(".sidebar-nav button");

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = "";
    }
    tab.className = "current";
}
