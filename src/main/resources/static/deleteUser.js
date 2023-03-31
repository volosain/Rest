const DeleteModal = document.getElementById('ModalDelete')
DeleteModal.addEventListener('show.bs.modal', event => {

    const button = event.relatedTarget

    console.log(button)
    const userId = button.getAttribute('data-bs-userId')
    const userName = button.getAttribute('data-bs-userName')
    const userLastName = button.getAttribute('data-bs-userSurname')

    const userEmail = button.getAttribute('data-bs-userEmail')
    console.log(button)

    const modalUserId = DeleteModal.querySelector('#userIdDelete')
    const modalUserName = DeleteModal.querySelector('#userNameDelete')
    const modalUserLastName = DeleteModal.querySelector('#userLastNameDelete')

    const modalUserEmail = DeleteModal.querySelector('#userEmailDelete')

    modalUserId.value = userId
    modalUserName.value = userName
    modalUserLastName.value = userLastName

    modalUserEmail.value = userEmail

})

const formDelete = document.getElementById('formDelete')
formDelete.addEventListener('submit', e =>{
    e.preventDefault();
    const formData = new FormData(formDelete);
    fetch("api/admin/"+formData.get("id"), {
        method: "DELETE"
    })
        .then(() => getUsers());
    $("#ModalDelete").modal("hide");
    formDelete.reset();
})