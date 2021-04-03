<template>
    <div>
        <AdminHeader />
         <div class="container">
    <div class="row gutters">

    <!------PERSONAL DETAILS  FORM--> 
    <div class="col-xl-12 col-lg-9 col-md-12 col-sm-12 col-12">

  <div class="card">
      <div class="card-body">
        <div class="account-settings">
          <div class="user-profile">
            <div class="user-avatar">
              <img src="../assets/logo_draft1.png" alt="Maxwell Admin">
            </div>
          </div>
          <div class="about">
            <h5>Add a new staff member</h5>
            <div class="form-group">
                <select required v-model="personType">
                    <option>ADMIN</option>
                    <option>TECHNICIAN</option>
                </select>
          </div>
          </div>
        </div>
      </div>
  </div>
    <div class="card ">
      <div class="card-body">
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <h6 class="mb-2 text-primary">Staff's Detail</h6>
          </div>
          
        
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="fullName">Staff's Username</label>
              <input  v-model="username" type="text" class="form-control" id="fullName" placeholder="Enter Username">
            </div>
          </div>

          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="eMail">Staff's Email</label>
              <input v-model="email"  type="email" class="form-control" id="eMail" placeholder="Enter email">
            </div>
          </div>

          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="phone">Staff's Temporary Password</label>
              <input v-model="password" type="password" class="form-control" id="phone" placeholder="Enter Password">
            </div>
          </div>
          <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
            <div class="form-group">
              <label for="website">Confirm Temporary Password</label>
              <input v-model="confirmPassword" type="password" class="form-control" id="website" placeholder="Confirm password">
            </div>
          </div>
        </div>


        <!---buttons ---> 
        <div class="row gutters">
          <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <div class="text-center">
              <button @click="showAddModal()" type="button" id="submit" name="submit" class="btn btn-primary">
                Update
              </button>
               <b-modal v-model="show" title="Create Staff Account" @ok="createAccount(username, email, password, confirmPassword, personType)">
                <p id="add">Are you sure you want to add {{username}} to the staff members?</p>
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

<script src="./AddStaffPage.js">
</script>

<style scoped>
#add{
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