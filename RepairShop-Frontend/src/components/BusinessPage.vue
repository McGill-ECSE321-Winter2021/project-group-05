<template>
    <div id="businessPage">
        <AdminHeader v-if="this.render"/>

    <div class="container" v-if="this.render">
    <div class="row gutters">

    <!----PERSONAL DETAILS TEXT -->
    <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
    <div class="card h-100">
      <div class="card-body">
        <div class="account-settings">
          <div class="user-profile">
            <div class="user-avatar">
              <img src="../assets/logo_draft1.png" alt="Maxwell Admin">
            </div>
          </div>
          <div class="about">
            <h5>Business Information</h5>
            <p>Name : <strong>{{name}}</strong> </p>
            <p>Email : <strong>{{email}}</strong></p>
            <p>Address : <strong>{{address}}</strong></p>
            <p>Phone Number : <strong>{{phoneNumber}}</strong></p>
          </div>
        </div>
      </div>
    </div>
    </div>

    <!------PERSONAL DETAILS  FORM-->
    <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
    <div class="card h-100">
      <div class="card-body">
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <h6 class="mb-2 text-primary">Business Details</h6>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="fullName">Business Name</label>
              <input  v-model="name" type="text" class="form-control" id="fullName" placeholder="Enter business name">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="eMail">Business Email</label>
              <input v-model="email"  type="email" class="form-control" id="eMail" placeholder="Enter email ID">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="phone">Business Address</label>
              <input v-model="address" type="text" class="form-control" id="phone" placeholder="Enter business address">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="website">Business Phone Number</label>
              <input v-model="phoneNumber" type="text" class="form-control" id="website" placeholder="Enter phone number">
            </div>
          </div>
        </div>

        <!---buttons --->
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <div class="text-center">
              <button @click="showUpdateModal()" type="button" id="submit" name="submit" class="btn btn-primary">
                Update
              </button>
               <b-modal v-model="showUpdate" title="Update Account" @ok="updateBusiness(name, address, phoneNumber, email)">
                <p id="update">Are you sure you want to update the business details?</p>
              </b-modal>
            </div>
          </div>
        </div>

      </div>
    </div>
    </div>
    </div>
    </div>
    <div v-if="!this.render">
       <label>Please login to continue</label>
    </div>
    </div>
</template>

<script src="./BusinessPage.js"></script>

<style scoped>
#update{
  color: #007ae1;
}
.container{
    margin-top: 50px;
}
body {
    margin: 0;
    padding-top: 40px;
    color: #2e323c;
    background: #f5f6fa;
    position: relative;
    height: 100%;
}
.account-settings .user-profile {
    margin: 0 0 1rem 0;
    padding-bottom: 1rem;
    text-align: center;
}
.account-settings .user-profile .user-avatar {
    margin: 0 0 1rem 0;
}
.account-settings .user-profile .user-avatar img {
    width: 150px;
    height: 150px;
    -webkit-border-radius: 100px;
    -moz-border-radius: 100px;
    border-radius: 100px;
}
.account-settings .user-profile h5.user-name {
    margin: 0 0 0.5rem 0;
}
.account-settings .user-profile h6.user-email {
    margin: 0;
    font-size: 0.8rem;
    font-weight: 400;
    color: #9fa8b9;
}
.account-settings .about {
    margin: 2rem 0 0 0;
    text-align: center;
}
.account-settings .about h5 {
    margin: 0 0 15px 0;
    color: #007ae1;
}
.account-settings .about p {
    font-size: 0.825rem;
}
.form-control {
    border: 1px solid #cfd1d8;
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
    font-size: .825rem;
    background: #ffffff;
    color: #2e323c;
}

.card {
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    background: #ffffff;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    border: 0;
    margin-bottom: 1rem;
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

.text-center{
    margin-top: 20px;
}

</style>
