import Controller from '@ember/controller';
import { get } from '@ember/object';
import { inject as service } from '@ember/service';

export default Controller.extend({
  auth: service(),

  actions: {
    login() {
      get(this, 'auth')
        .login(get(this, 'email'), get(this, 'password'))
        .then(() => this.transitionToRoute('users.index'))
        .catch(() => console.log('TOOD show error msg, didnt auth'));
    }
  }
});
