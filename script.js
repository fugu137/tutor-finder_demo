function toggleTutorView() {
    const sideCol = document.getElementsByClassName("sidecol")[0];
    const main = document.getElementsByTagName("main")[0];

    if (sideCol.classList.contains("open")) {
        sideCol.classList.remove("open");
        main.classList.remove("open");

    } else {
        sideCol.classList.add("open");
        main.classList.add("open");
    }
}

function toggleMainMenu(tab) {
    var tabs = document.querySelectorAll(".sidebar-nav button");

    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = "";
    }
    tab.className = "current";
}