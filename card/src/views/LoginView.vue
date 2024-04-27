<template>
  <v-container class="center login-background">
    <img src="../assets/card.jpg" alt="Game Photo" class="game-photo">
    <v-sheet width="500" class="form-container">
      <v-form ref="loginForm" v-model="loginForm" @submit.prevent="login">

        <v-text-field v-model="playerName" label="Name" :rules="[
          (v) => !!v || 'Required',
        ]" prepend-icon="mdi-account" type="text" required 
        class="text-field"
        />
        <!-- -------------------button -------------------->
        <div class="text-center mt-3">
          <v-btn type="submit" class="btn" dark :disabled="!loginForm">
            <span v-if="!loading">PLAY</span>
            <v-progress-circular v-else indeterminate color="white"></v-progress-circular>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>
  </v-container>
</template>
  
<script>
import utils from '@/utils/utils';
export default {
  name: "LoginView",
  props: {
    source: String,
  },
  data() {
    return {
      step: 1,
      loginForm: false,
      errorAlert: false,
      loading: false,
      poster: null,
      posterPreviewPath: null,
      playerName: '',
    };
  },
  async created() {},
  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;
        this.loading = true;
        
          const resp = await utils.https.post("/player/login", {
          playerName: this.playerName,
        });
        if (resp.status === 200) {
          const data = await resp.json();
          this.$router.push({ path: '/card', query: { playerName: this.playerName} });
          console.log("photo",this.playerPhoto);
          if (data) {
            this.$store.commit("setLoginUser", data);
            // this.$router.push({path:"/home"});
            console.log("successful");
            } else {
              this.$router.push({ path: "/" });
            }
          }
      }else {
          this.errorAlert = true;
        }
        this.loading = false;
      },
      onChangePoster(poster) {
      this.posterPreviewPath = URL.createObjectURL(poster);
    },
    },
  };
</script>
  
<style scoped>
.center {
  display: flex;
  justify-content: center;
  height: 100vh;
}

.login-background {
  position: relative;
}

.game-photo {
  position: absolute;
  top: 0;
  left: 0;
  width: 80%;
  height: 80%;
  object-fit: cover;
}

.form-container {
  /* border: 1px solid #ccc; */
  padding: 20px;
  /* border-radius: 10px; */
  /* background-color:white !important; */
  margin-top: 100px;
}
.btn {
  background-color: blue !important;
  /* color: white !important; */
}
.close-button {
  text-align: right;
}
.close-button:hover .v-btn{
  background-color: transparent !important;
}
.text-field{
  background-color: white;
}
</style>
