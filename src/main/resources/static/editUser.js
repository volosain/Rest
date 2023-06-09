const editModal = document.getElementById('ModalEdit')
editModal.addEventListener('show.bs.modal', event => {

    const button = event.relatedTarget
    // console.log(button)

    const userId = button.getAttribute('data-bs-userId')
    const userName = button.getAttribute('data-bs-userName')
    const userLastName = button.getAttribute('data-bs-userSurname')

    const userEmail = button.getAttribute('data-bs-userEmail')

    const modalUserId = editModal.querySelector('#userId')
    const modalUserName = editModal.querySelector('#userName2')
    const modalUserLastName = editModal.querySelector('#userSurname')

    const modalUserEmail = editModal.querySelector('#userEmail')

    modalUserId.value = userId
    modalUserName.value = userName
    modalUserLastName.value = userLastName

    modalUserEmail.value = userEmail

})

const formEdit = document.getElementById("formEdit");
formEdit.addEventListener('submit', e => {
    e.preventDefault();

    const formData = new FormData(formEdit);
    const object = {
        roles:[]
    };

    formData.forEach((value, key) => {
        console.log(key)
        if (key === "rolesId"){

            const roleId = value.split(" ")[0];
            const roleName = value.split(" ")[1];
            const role = {
                id : roleId,
                role : roleName
            };

            object.roles.push(role);
        } else {
            object[key] = value;
        }
    });
    fetch("api/admin/", {
        method: "PUT",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(object)
    })
        .then(() => getUsers());
    $("#ModalEdit").modal("hide");
    formEdit.reset();
})