<template>
  <div>
    <CustomerHeader />
  <div class="container">
    <div class="row gutters">
    
    <!----PERSONAL DETAILS TEXT --> 
    <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
    <div class="card h-100">
      <div class="card-body">
        <div class="account-settings">
          <div class="user-profile">
            <div class="user-avatar">
              <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Maxwell Admin">
            </div>
            <h5 class="user-name">{{username}}</h5>
            <h6 class="user-email">{{email}}</h6>
          </div>
          <div class="about">
            <h5>About</h5>
            <p>I'm {{username}}. Full Stack Designer I enjoy creating user-centric, delightful and human experiences.</p>
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
            <h6 class="mb-2 text-primary">Personal Details</h6>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="fullName">Username</label>
              <input  v-model="username" type="text" class="form-control" id="fullName" placeholder="Enter full name">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="eMail">Email</label>
              <input v-model="email"  type="email" class="form-control" id="eMail" placeholder="Enter email ID">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="phone">Password</label>
              <input v-model="password" type="password" class="form-control" id="phone" placeholder="Enter Password">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="website">Confirm Password</label>
              <input v-model="confirmPassword" type="password" class="form-control" id="website" placeholder="Confirm password">
            </div>
          </div>
        </div>

        <!----CARD DETAILS --->
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <h6 class="mt-3 mb-2 text-primary">Credit Card Information</h6>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="Street">Name on the Card</label>
              <input v-model="username" type="name" class="form-control" id="Street" placeholder="Enter Username">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="ciTy">Card Number</label>
              <input v-model="cardNumber" type="name" class="form-control" id="ciTy" placeholder="Enter Card Number">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="sTate">CVV</label>
              <input v-model="cvv" type="text" class="form-control" id="sTate" placeholder="Enter CVV">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="zIp">Expiry Date (YYYY-MM-DD)</label>
              <input v-model="expiry" type="text" class="form-control" id="zIp" placeholder="Expiry Date">
            </div>
          </div>
        </div>

        <!---buttons ---> 
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <div class="text-right">
              <b-button @click="showDeleteModal()" variant="outline-danger" type="button" id="submit" name="submit" >Delete Account</b-button>
              <b-modal v-model="showDelete" title="Delete Account" @ok="deleteAccount(email)">
                <p id="warning">Are you sure you want to delete your account? This process is irreversible.</p>
              </b-modal>
              <button @click="showUpdateModal()" type="button" id="submit" name="submit" class="btn btn-primary">
                Update
              </button>
               <b-modal v-model="showUpdate" title="Update Account" @ok="updateAccount(username, email, password,confirmPassword, cvv, cardNumber, expiry)">
                <p id="update">Are you sure you want to update your account details?</p>
              </b-modal>
            </div>
          </div>
        </div>

      </div>
    </div>
    </div>
    </div>
    </div>
  </div>

</template>

<script src = "./CustomerAccount.js">

</script>

<style scoped>
#update{
  color: #007ae1;
}
#warning{
  color: red;
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
    width: 90px;
    height: 90px;
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
    background: #ffffff;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    border: 0;
    margin-bottom: 1rem;
}

</style>
