import Controller from '@ember/controller';
import { get } from '@ember/object';

export default Controller.extend({
  actions: {
    save(e) {
      e.preventDefault();

      let values = {
        username: e.target.username.value,
        email: e.target.email.value,
        role: e.target.role.value,
        active: e.target.active.checked,
      };

      if (!values.username || !values.email || !values.role) {
        console.log('TODO missing fields, tell the user');
        return;
      }

      if (get(this, 'model.id') === 'new') {
        console.log('TODO create user', values)
        // new user mutation
      }
      else {
        console.log('TODO update user', get(this, 'model.id'), values);
        // update user mutation
      }
    },

    cancel() {
      this.transitionToRoute('users.index');
    }
  }
});
