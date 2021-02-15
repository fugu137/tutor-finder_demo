//Initialization Methods//
const url = new URL("http://localhost:8080/api/tutor");

loadProfiles();

function loadProfiles() {
    const mainSection = document.getElementById("browse-tab");
    // const fragment = document.createDocumentFragment();

    const fragment = document.querySelectorAll(".section-template")[0].content.cloneNode(true);
    // fragment.appendChild(profilePane);

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

                const profile = document.querySelectorAll(".profile-template")[0].content.cloneNode(true);
                profile.querySelectorAll(".title-pane h5")[0].textContent += firstName + " " + surname;
                profile.querySelectorAll("#email-pane")[0].textContent = email;

                const subjectsPane = profile.querySelectorAll(".subjects-pane")[0];

                for (var j = 0; j < subjects.length; j++) {
                    const blankBox = profile.querySelectorAll(".subject-box")[0];
                    const subjectBox = blankBox.cloneNode(true);
                    subjectBox.textContent = subjects[j];
                    subjectsPane.insertBefore(subjectBox, blankBox);
                }

                if (imagePath !== null) {
                    profile.querySelectorAll(".profile-pic")[0].src = imagePath;
                }

                const profilePane = fragment.querySelectorAll("section")[0];
                profilePane.appendChild(profile);
            }
            // const mainFooter = document.querySelectorAll("main footer")[0];

            // mainSection.insertBefore(fragment, mainFooter);
            // mainFooter.scrollIntoView({ behavior: "smooth" });
            mainSection.appendChild(fragment);
        });

}

function loadMoreProfiles() {
    const fragment = document.createDocumentFragment();

}

//End Initialization Methods//
const modal = document.getElementsByClassName("modal")[0];
const activeFilters = new Array();

function toggleMainTab(tabButton, tabPane) {
    const tabButtons = document.querySelectorAll(".sidebar-nav button");
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

        const index = activeFilters.indexOf(button);
        activeFilters.splice(index, 1);

        setDeselected(button);

        if (activeFilters.length < 1) {
            allButton.classList.add("selected");
        }

    } else if (button === allButton) {

        for (var i = 0; i < activeFilters.length; i++) {
            activeFilters[i].classList.remove("selected");
        }
        activeFilters.length = 0;

        button.classList.remove("deselected");
        button.classList.add("selected");

    } else {
        button.classList.remove("deselected");
        button.classList.add("selected");
        activeFilters.push(button);

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
    modal.style.display = "block";
}

function closeAddTutorPopup() {
    modal.style.display = "none";
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
            mode: "no-cors",
            cache: "no-cache",
            credentials: "same-origin",
            // headers: {
            //     "Content-Type": "multipart/form-data"
            // },
            body: formData
        })
        .then(response => {
            closeAddTutorPopup();
            alert("Tutor successfully added!");
        })
        .catch(error => {
            console.error("Unable to add tutor: " + error);
        });
        
        
    
}
//End Form Methods//
