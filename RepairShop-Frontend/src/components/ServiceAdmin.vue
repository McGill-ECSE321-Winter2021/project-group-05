<template>
  <div id="serviceAdmin">
    <h2>RepairShop</h2>
    <label align=CENTER>Create new service</label>
    <label align=CENTER> </label>
    <table align="CENTER">
      <tr>
          <td>
              <input type="text" placeholder="Enter name">
          </td>
          <td>
              <input type="number" step="0.01" placeholder="Enter cost">
          </td>
          <td>
              <input type="number" placeholder="Enter duration">
          </td>
      </tr>
    </table>
    <Button align=CENTER>Create</Button>
    <p>
      <span style="color:red">Error: Message text comes here</span>
    </p>
  </div>
</template>

<script>
</script>


<style>
  #serviceAdmin {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
