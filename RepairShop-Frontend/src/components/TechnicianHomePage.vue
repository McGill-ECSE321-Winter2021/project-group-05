<template>
    <div>
            <TechnicianHeader />
            <div >
              <WelcomePage  />
            </div>
        </div>
</template>

<script>
import TechnicianHeader from './TechnicianHeader'
import WelcomePage from '../components/WelcomePage.vue'

export default {
    name: "TechnicianHomePage",
    components: {
        TechnicianHeader,
        WelcomePage
    }
}
</script>

<style scoped>

</style>
