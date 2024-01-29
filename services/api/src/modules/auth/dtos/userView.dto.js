class UserViewDto {
  constructor(user) {
    this.id = user.id ?? user._id;
    this.role = user.role;
    this.name = user.name;
    this.surname = user.surname;
    this.email = user.email;
    this.fullName = `${this.name} ${this.surname}`;
    this.userName = user.userName;
    this.photo = user.photo;
    this.banner = user.banner;
    this.about = user.about;
    this.createdAt = user.createdAt;
    this.updatedAt = user.updatedAt;
  }
}

export default UserViewDto;
