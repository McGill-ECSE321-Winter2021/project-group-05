<template>
    <div id="businessPage">
        <AdminHeader />
        <div id="businessForm">
            <form>
                <label>Email:</label>
                <input type="email" required v-model="email">

                <label>Name: </label>
                <input required v-model="name">

                <label>Address: </label>
                <input required v-model="address">

                <label>Phone Number: </label>
                <input required v-model="phoneNumber">

                <button v-on:click="updateBusiness(name, address, phoneNumber, email)">Update Info</button>
            </form>
        </div>
    </div>
</template>

<script src="./BusinessPage.js"></script>

<style scoped>
#businessPage{
}
#businessForm {
    background-color: #F6F6F6;
    align-items: center;
    display: flex;
    flex-direction: column;
    height: 100vh;
}
form {
    width: 50%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    border: 1px solid lightgray;
}  
label {
    font-family: 'Times New Roman', Times, serif;
    font-size: 14px;
}

button {
    background-color: #2373F7;
    color: white;
    margin-top: 16px;
}

</style>