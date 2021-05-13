//Initialization Methods//
const url = new URL("http://localhost:8080/api/tutor");
const idTable = new Map();

loadProfiles();

function loadProfiles() {
    const mainSection = document.getElementById("browse-tab");
    const fragment = document.querySelectorAll(".section-template")[0].content.cloneNode(true);


    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {

            for (var i = 0; i < data.length; i++) {
                const firstName = data[i].firstName;
                const surname = data[i].surname;
                const email = data[i].email;
                const subjects = data[i].subjects;
                const imagePath = data[i].imagePath;
                const id = data[i].id;

                const profileFrag = document.querySelectorAll(".profile-template")[0].content.cloneNode(true);
                profileFrag.querySelectorAll(".title-pane h5")[0].textContent = firstName + " " + surname;
                profileFrag.querySelectorAll(".email-pane")[0].textContent = email;

                const subjectsPane = profileFrag.querySelectorAll(".subjects-pane")[0];
                const blankBox = profileFrag.querySelectorAll(".subject-box")[0];

                for (var j = 0; j < subjects.length; j++) {
                    const subjectBox = blankBox.cloneNode(true);
                    subjectBox.textContent = subjects[j];
                    subjectsPane.insertBefore(subjectBox, blankBox);
                }

                if (imagePath !== null) {
                    profileFrag.querySelectorAll(".profile-pic")[0].src = imagePath;
                }

                const profilePane = fragment.querySelectorAll("section")[0];
                profilePane.appendChild(profileFrag);

                const profile = profilePane.getElementsByClassName("profile")[i];
                const name = "profile" + i;
                profile.id = name;
                idTable.set(name, id);

                const closeButton = profile.querySelectorAll(".close-button")[0];
                closeButton.onclick = function () { removeTutor(profile) };
            }

            mainSection.appendChild(fragment);
        });

}


//End Initialization Methods//
const modal = document.getElementsByClassName("modal")[0];
const activeFilters = new Set();

function toggleMainTab(tabButton, tabPane) {
    const tabButtons = document.querySelectorAll(".menubar-nav button");
    const tabPanes = document.querySelectorAll(".tab");

    for (var i = 0; i < tabButtons.length; i++) {
        tabButtons[i].classList.remove("current");
    }
    tabButton.classList.add("current");

    for (var i = 0; i < tabPanes.length; i++) {
        tabPanes[i].classList.remove("open");
    }
    document.getElementById(tabPane).classList.add("open");
}

function toggleFilter(event) {
    if (event.target.nodeName !== "BUTTON") {
        return;
    }

    const button = event.target;
    const allButton = document.getElementById("all-button");

    if (button.className === "selected") {
        if (button === allButton) {
            return;
        }
        button.classList.remove("selected");
        activeFilters.delete(button);

        setDeselected(button);

        if (activeFilters.size < 1) {
            allButton.classList.add("selected");
        }

    } else if (button === allButton) {

        for (var b of activeFilters.values()) {
            b.classList.remove("selected");
        }
        activeFilters.clear();

        button.classList.remove("deselected");
        button.classList.add("selected");

    } else {
        button.classList.remove("deselected");
        button.classList.add("selected");
        activeFilters.add(button);

        if (allButton.classList.contains("selected")) {
            allButton.classList.remove("selected");
        }
    }
}


//Helper Function//
function setDeselected(button) {
    button.classList.add("deselected");
    button.addEventListener("mouseleave", () => {
        button.classList.remove("deselected");
    });
}
//End Helper Functions//


function openAddTutorPopup() {
    modal.style.display = "flex";
}

function closeAddTutorPopup() {
    modal.style.display = "none";
}

async function removeTutor(profile) {
    if (confirm("Are you sure you want to remove this tutor's profile?")) {
        const id = idTable.get(profile.id);

        fetch(url + "/" + id, {
            method: "DELETE",
            body: null
        })
            .then(response => response.text())
            .then(data => {
                console.log(data);
                location.reload();
            })
            .catch(error => {
                console.error("Error: " + error);
            });

    }
}

function openTutorDetails(event) {
    if (event.target.className !== "close-button") {
        alert('No tutor details to see in demo version!\nIn the finished product, users would be able to see a popup window with full tutor details.')
    }
}

//Form Methods//
var fileList;

function previewImg(upload) {
    fileList = upload.files;

    if (fileList.length > 0) {

        var file = fileList[0];
        var url = URL.createObjectURL(file);

        var image = document.getElementById("preview");
        image.src = url;
        image.style.display = "block";

        var uploadLabel = document.getElementById("upload-label");
        uploadLabel.style.display = "none";

    } else {
        console.log("No files found!");
    }
}

function processForm() {
    const firstName = document.getElementById("firstname").value;
    const surname = document.getElementById("lastname").value;
    const email = document.getElementById("email").value;

    var checkedBoxes = document.querySelectorAll("input[type=checkbox]:checked");
    var subjects = Array.from(checkedBoxes).map(checkedBox => checkedBox.value);

    if (fileList === undefined) {
        submitTutor(firstName, surname, email, subjects);

    } else {
        const pictureFile = fileList[0];
        submitTutor(firstName, surname, email, subjects, pictureFile);
    }

}

function submitTutor(firstName, surname, email, subjects, picture) {
    const formData = new FormData();
    formData.append("firstName", firstName);
    formData.append("surname", surname);
    formData.append("email", email);
    formData.append("subjects", subjects);

    if (picture !== undefined) {
        formData.append("picture", picture);
    }

    fetch(url, {
        method: "POST",
        body: formData
    })
        .then(function () {
            closeAddTutorPopup();
            // alert("Tutor successfully added!");
            refreshProfiles();
        })
        .catch(error => {
            console.error("Unable to add tutor: " + error);
        });
}

function refreshProfiles() {
    location.reload();

    const mainFooter = document.querySelectorAll("main footer")[0];
    mainFooter.scrollIntoView({ behavior: "smooth", block: "start" });
}
//End Form Methods//
